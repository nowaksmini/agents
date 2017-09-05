package com.mini.smartroad.service.helper;

import com.mini.smartroad.DriverRuntimeInfo;
import com.mini.smartroad.GroupRuntimeInfo;
import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.Main;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.negotiate.FindStationsInDto;
import com.mini.smartroad.dto.out.negotiate.FindStationsOutDto;
import com.mini.smartroad.dto.out.negotiate.StationOutDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.model.*;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DriverServiceFindStationsBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceFindStationsBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        super(receiver, ontology, protocol, inputContent);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.FAILURE);
        message.setOntology(getOntology());
        message.setProtocol(getProtocol());
        message.addReceiver(getReceiver());
        try {
            message.setContentObject(findStations((FindStationsInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private FindStationsOutDto findStations(FindStationsInDto findStationsInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        FindStationsOutDto findStationsOutDto = new FindStationsOutDto(statusOutDto);
        String userToken = findStationsInDto.getToken();
        if (userToken == null || userToken.trim().isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_NO_INPUT_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return findStationsOutDto;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        List foundUsers = session.createCriteria(UserEntity.class).add(Restrictions.eq("token", userToken)).list();
        if (foundUsers == null || foundUsers.isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_USER_WITH_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findStationsOutDto;
        }
        if (foundUsers.size() != 1) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_UNIQUE);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findStationsOutDto;
        }

        Long distance = findStationsInDto.getDistance();
        DriverRuntimeInfo driverRuntimeInfo = Main.drivers.get(userToken);
        if (driverRuntimeInfo == null) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_INFORMATION_ABOUT_CURRENT_LOCATION);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findStationsOutDto;
        }
        double latitude = driverRuntimeInfo.getLatitude();
        double longitude = driverRuntimeInfo.getLongitude();
        double maxLatitude;
        double maxLongitude;
        double minLatitude;
        double minLongitude;
        if (distance == null) {
            maxLatitude = Utils.MAX_LATITUDE;
            maxLongitude = Utils.MAX_LONGITUDE;
            minLatitude = Utils.MIN_LATITUDE;
            minLongitude = Utils.MIN_LONGITUDE;
        } else {
            maxLatitude = Math.min(latitude + distance * Utils.KILOMETER_TO_DEGREES_LATITUDE, Utils.MAX_LATITUDE);
            maxLongitude = Math.min(longitude + distance * Utils.KILOMETER_TO_DEGREES_LONGITUDE, Utils.MAX_LONGITUDE);
            minLatitude = Math.max(latitude - distance * Utils.KILOMETER_TO_DEGREES_LATITUDE, Utils.MIN_LATITUDE);
            minLongitude = Math.max(longitude - distance * Utils.KILOMETER_TO_DEGREES_LONGITUDE, Utils.MIN_LONGITUDE);
        }
        session.close();
        List<StationOutDto> stations = findStations(minLatitude, maxLatitude, minLongitude, maxLongitude,
                (UserEntity) foundUsers.get(0));
        findStationsOutDto.setStations(stations);
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return findStationsOutDto;
    }

    private List<StationOutDto> findStations(double minLatitude, double maxLatitude, double minLongitude,
                                             double maxLongitude, UserEntity userEntity) {
        List<StationOutDto> foundStations = new LinkedList<>();

        Session session = HibernateUtils.getSessionFactory().openSession();
        List stations = session.createCriteria(StationEntity.class)
                .add(Restrictions.between("latitude", minLatitude, maxLatitude))
                .add(Restrictions.between("longitude", minLongitude, maxLongitude)).list();
        if (stations != null) {
            for (Object station : stations) {
                StationEntity stationEntity = (StationEntity) station;
                AddressEntity addressEntity = stationEntity.getAddress();
                AddressDto addressDto = new AddressDto(addressEntity.getCountry(), addressEntity.getProvince(),
                        addressEntity.getCity(), addressEntity.getStreet(), addressEntity.getNumber(),
                        addressEntity.getExtraNumber(), addressEntity.getPostalCode());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.add(Calendar.HOUR, Utils.LIKE_TIME_DURATION);
                Integer counter = 0;
                Integer points = 0;
                Integer minCarsAmount = 0;
                ActionType actionType = null;
                StationStrategyEntity stationStrategy = stationEntity.getStationStrategy();
                if (Main.groups.containsKey(stationEntity.getToken())) {
                    GroupRuntimeInfo groupRuntimeInfo = Main.groups.get(stationEntity.getToken());
                    points = groupRuntimeInfo.getCurrentReward();
                    counter = groupRuntimeInfo.getCurrentNumberOfCars();
                    minCarsAmount = counter;
                    List<String> participants = Main.currentGroupParticipants.get(stationEntity.getToken());
                    if (participants.size() > 0 && participants.get(0).equals(userEntity.getToken())) {
                        actionType = ActionType.REPRESENTATE;
                    } else if (participants.contains(userEntity.getToken())) {
                        actionType = ActionType.ATTEND;
                    }
                } else {
                    points = stationStrategy.getPointsGroupSize0();
                    minCarsAmount = stationStrategy.getGroupSize0();
                }
                StationOutDto stationOutDto = new StationOutDto(addressDto, stationEntity.getName(),
                        stationEntity.getEmail(), stationEntity.getLogo(),
                        stationEntity.getToken(), stationEntity.getPhone(), stationEntity.getLongitude(),
                        stationEntity.getLatitude(), counter, points, minCarsAmount, actionType);
                foundStations.add(stationOutDto);
            }
        }

        session.close();

        return foundStations;
    }

    @Override
    public boolean done() {
        return sent;
    }

}