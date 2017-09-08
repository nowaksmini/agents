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
import java.util.List;

public class DriverServiceAcceptInvitationBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceAcceptInvitationBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(acceptInvitation((ActionInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusOutDto acceptInvitation(ActionInDto actionInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        // TODO
        return statusOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}