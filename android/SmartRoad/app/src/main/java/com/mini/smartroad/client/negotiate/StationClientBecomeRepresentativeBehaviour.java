package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;

public class StationClientBecomeRepresentativeBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationClientBecomeRepresentativeBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(decide((BaseInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusOutDto decide(BaseInDto baseInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        String userToken = baseInDto.getToken();
        if (userToken == null || userToken.trim().isEmpty()) {
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_NO_INPUT_TOKEN);
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return statusOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}