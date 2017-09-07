package com.mini.smartroad.service.helper;

import com.mini.smartroad.DriverRuntimeInfo;
import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.Main;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.negotiate.FindUsersInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.negotiate.FindUsersOutDto;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.UserEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DriverServiceFindUsersBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceFindUsersBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(findUsers((FindUsersInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private FindUsersOutDto findUsers(FindUsersInDto findUsersInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        FindUsersOutDto findUsersOutDto = new FindUsersOutDto(statusOutDto);
        String userToken = findUsersInDto.getToken();
        if (userToken == null || userToken.trim().isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_NO_INPUT_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return findUsersOutDto;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        List foundUsers = session.createCriteria(UserEntity.class).add(Restrictions.eq("token", userToken)).list();
        if (foundUsers == null || foundUsers.isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_USER_WITH_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findUsersOutDto;
        }
        if (foundUsers.size() != 1) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_UNIQUE);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findUsersOutDto;
        }

        List foundStations = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", findUsersInDto.getStationToken())).list();
        if (foundStations == null || foundStations.isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_STATION_WITH_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findUsersOutDto;
        }
        if (foundStations.size() != 1) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_STATION_UNIQUE);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return findUsersOutDto;
        }

        StationEntity stationEntity = (StationEntity) foundStations.get(0);
        session.close();

        Long distanceKm = 10L;
        List<String> potentialNegotiatorsTokens = new LinkedList<>();
        double latitude = stationEntity.getLatitude();
        double longitude = stationEntity.getLongitude();
        double maxLatitude = Math.min(latitude + distanceKm * Utils.KILOMETER_TO_DEGREES_LATITUDE, Utils.MAX_LATITUDE);
        double maxLongitude = Math.min(longitude + distanceKm * Utils.KILOMETER_TO_DEGREES_LONGITUDE, Utils.MAX_LONGITUDE);
        double minLatitude = Math.max(latitude - distanceKm * Utils.KILOMETER_TO_DEGREES_LATITUDE, Utils.MIN_LATITUDE);
        double minLongitude = Math.max(longitude - distanceKm * Utils.KILOMETER_TO_DEGREES_LONGITUDE, Utils.MIN_LONGITUDE);
        Set<String> currentDriversTokens = Main.drivers.keySet();
        for (String currentDriverToken : currentDriversTokens) {
            DriverRuntimeInfo driverRuntimeInfo = Main.drivers.get(currentDriverToken);
            if (driverRuntimeInfo.isWantsToNegotiate()) {
                double driverRuntimeInfoLatitude = driverRuntimeInfo.getLatitude();
                double driverRuntimeInfoLongitude = driverRuntimeInfo.getLongitude();
                if (driverRuntimeInfoLatitude <= maxLatitude && driverRuntimeInfoLatitude >= minLatitude) {
                    if (driverRuntimeInfoLongitude <= maxLongitude && driverRuntimeInfoLongitude >= minLongitude) {
                        potentialNegotiatorsTokens.add(driverRuntimeInfo.getToken());
                    }
                }
            }
        }

        findUsersOutDto.setAvailableUsers(potentialNegotiatorsTokens);
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return findUsersOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}