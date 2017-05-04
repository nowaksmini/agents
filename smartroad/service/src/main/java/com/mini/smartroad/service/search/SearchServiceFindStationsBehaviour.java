package com.mini.smartroad.service.search;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.FindStationsInDto;
import com.mini.smartroad.dto.out.FindStationsOutDto;
import com.mini.smartroad.dto.out.StationOutDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.model.ActionEntity;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationDetailsEntity;
import com.mini.smartroad.model.UserEntity;
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

public class SearchServiceFindStationsBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public SearchServiceFindStationsBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
        String userToken = findStationsInDto.getUserToken();
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
            statusOutDto.setMessage(MessageProperties.ERROR_NO_USER_WITH_TOKEN + userToken);
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
        double latitude = findStationsInDto.getLatitude();
        double longitude = findStationsInDto.getLongitude();
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
        List<StationOutDto> foundStations = new LinkedList<StationOutDto>();

        Session session = HibernateUtils.getSessionFactory().openSession();
        List stations = session.createCriteria(StationDetailsEntity.class)
                .add(Restrictions.between("latitude", minLatitude, maxLatitude))
                .add(Restrictions.between("longitude", minLongitude, maxLongitude)).list();
        if (stations != null) {
            for (Object station : stations) {
                StationDetailsEntity stationDetailsEntity = (StationDetailsEntity) station;
                AddressEntity addressEntity = stationDetailsEntity.getAddress();
                AddressDto addressDto = new AddressDto(addressEntity.getCountry(), addressEntity.getProvince(),
                        addressEntity.getCity(), addressEntity.getStreet(), addressEntity.getNumber(),
                        addressEntity.getExtraNumber(), addressEntity.getPostalCode());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.add(Calendar.HOUR, Utils.LIKE_TIME_DURATION);
                Integer likes = findActiveActions(stationDetailsEntity, calendarEnd.getTime(), ActionType.LIKE, session);
                Integer confirms = findActiveActions(stationDetailsEntity, calendarEnd.getTime(), ActionType.CONFIRM, session);
                StationOutDto stationOutDto = new StationOutDto(addressDto, stationDetailsEntity.getName(),
                        stationDetailsEntity.getFullName(), stationDetailsEntity.getEmail(), stationDetailsEntity.getLogo(),
                        stationDetailsEntity.getToken(), stationDetailsEntity.getPhone(), stationDetailsEntity.getLongitude(),
                        stationDetailsEntity.getLatitude(), likes, confirms,
                        findActiveActionForUser(stationDetailsEntity, userEntity, calendarEnd.getTime(), session));
                foundStations.add(stationOutDto);
            }
        }

        session.close();

        return foundStations;
    }

    private Integer findActiveActions(StationDetailsEntity stationDetailsEntity, Date end, ActionType actionType, Session session) {
        Integer activeActions = 0;
        List actions = session.createCriteria(ActionEntity.class).add(Restrictions.le("dateFrom", end))
                .add(Restrictions.ge("dateTo", new Date()))
                .add(Restrictions.eq("station", stationDetailsEntity))
                .add(Restrictions.eq("actionType", actionType))
                .add(Restrictions.eq("value", Boolean.TRUE)).list();
        if (actions != null) {
            activeActions = actions.size();
        }
        return activeActions;
    }

    private ActionType findActiveActionForUser(StationDetailsEntity stationDetailsEntity, UserEntity userEntity, Date end, Session session) {
        ActionType actionType = null;
        List actions = session.createCriteria(ActionEntity.class).add(Restrictions.le("dateFrom", end))
                .add(Restrictions.ge("dateTo", new Date()))
                .add(Restrictions.eq("station", stationDetailsEntity))
                .add(Restrictions.eq("user", userEntity))
                .add(Restrictions.eq("value", Boolean.TRUE)).list();
        if (actions != null && !actions.isEmpty()) {
            actionType = ((ActionEntity) actions.get(0)).getActionType();
        }
        return actionType;
    }

    @Override
    public boolean done() {
        return sent;
    }

}