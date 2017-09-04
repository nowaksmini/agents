package com.mini.smartroad.service.login_register;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.login.StationLoginInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.login_register.LoginRegisterStationOutDto;
import com.mini.smartroad.model.StationEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class StationServiceLoginBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceLoginBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(loginStation((StationLoginInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private LoginRegisterStationOutDto loginStation(StationLoginInDto stationLoginInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        LoginRegisterStationOutDto loginRegisterStationOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("userName", stationLoginInDto.getUserName()))
                .add(Restrictions.eq("secretCode", stationLoginInDto.getSecretCode()))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_STATION_LOGIN);
            loginRegisterStationOutDto = new LoginRegisterStationOutDto(statusOutDto);
            return loginRegisterStationOutDto;
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_STATION_UNIQUE);
            loginRegisterStationOutDto = new LoginRegisterStationOutDto(statusOutDto);
            return loginRegisterStationOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        StationEntity stationEntity = (StationEntity) list.get(0);
        loginRegisterStationOutDto = new LoginRegisterStationOutDto(stationEntity.getToken(),
                stationEntity.getSecretCode(), statusOutDto);
        return loginRegisterStationOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}