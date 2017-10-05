package com.mini.smartroad.service.action;

import com.mini.smartroad.Main;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.dto.in.ActionInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DriverServiceRejectInvitationBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceRejectInvitationBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(rejectInvitation((ActionInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusOutDto rejectInvitation(ActionInDto actionInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        statusOutDto.setStatusType(StatusType.OK);
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}