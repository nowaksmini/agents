package com.mini.smartroad.service.action;

import com.mini.smartroad.DriverRuntimeInfo;
import com.mini.smartroad.GroupRuntimeInfo;
import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.Main;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.ActionInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.UserEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StationServiceConfirmUserCameBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceConfirmUserCameBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(confirm((ActionInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusOutDto confirm(ActionInDto actionInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        String userToken = actionInDto.getToken();
        if (userToken == null || userToken.trim().isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_NO_INPUT_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return statusOutDto;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        List foundUsers = session.createCriteria(UserEntity.class).add(Restrictions.eq("token", userToken)).list();
        if (foundUsers == null || foundUsers.isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_USER_WITH_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return statusOutDto;
        }
        if (foundUsers.size() != 1) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_UNIQUE);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return statusOutDto;
        }
        UserEntity userEntity = (UserEntity) foundUsers.get(0);

        List foundStations = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", actionInDto.getStationToken())).list();
        if (foundStations == null || foundStations.isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_STATION_WITH_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return statusOutDto;
        }
        if (foundStations.size() != 1) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_STATION_UNIQUE);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            session.close();
            return statusOutDto;
        }

        StationEntity stationEntity = (StationEntity) foundStations.get(0);
        session.close();

        GroupRuntimeInfo groupRuntimeInfo = Main.groups.get(stationEntity.getToken());
        if (groupRuntimeInfo != null) {
            if (groupRuntimeInfo.getFrom().isAfter(LocalDateTime.now()) && groupRuntimeInfo.getTo().isBefore(LocalDateTime.now())) {
                statusOutDto.setStatusType(StatusType.ERROR);
                statusOutDto.setMessage(MessageProperties.ERROR_GROUP_ALREADY_EXISTS);
                aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
                session.close();
                return statusOutDto;
            }
        }

        LocalDateTime localDateTimeFrom = LocalDateTime.now().withMinute(0).withSecond(0);
        LocalDateTime localDateTimeTo = LocalDateTime.now().withMinute(59).withSecond(59);
        int currentReward = 0;

        GroupRuntimeInfo newGroupRuntimeInfo = new GroupRuntimeInfo(stationEntity.getToken(),
                localDateTimeFrom, localDateTimeTo, 1, currentReward);
        Main.groups.put(stationEntity.getToken(), newGroupRuntimeInfo);
        List<String> participants = new LinkedList<>();
        participants.add(userEntity.getToken());
        Main.currentGroupParticipants.put(stationEntity.getToken(), participants);
        DriverRuntimeInfo driverRuntimeInfo = Main.drivers.get(userToken);
        driverRuntimeInfo.setWantsToNegotiate(false);
        driverRuntimeInfo.setGroupToken(stationEntity.getToken());
        Main.drivers.put(userToken, driverRuntimeInfo);

        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}