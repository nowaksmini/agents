package com.mini.smartroad.service.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.ActionInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

public class ActionServiceConfirmBehaviour extends ActionServiceBaseBehaviour {

    public ActionServiceConfirmBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            ActionInDto actionInDto = (ActionInDto) getInputContent();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, Utils.LIKE_TIME_DURATION);
            StatusOutDto statusOutDto = findPreviousAction(actionInDto, ActionType.LIKE,
                    Boolean.TRUE, Boolean.FALSE, calendar.getTime(), message, false);
            if (statusOutDto.getStatusType() == StatusType.ERROR) {
                message.setContentObject(statusOutDto);
            } else {
                message.setContentObject(createAction(actionInDto, message));
            }
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }
}