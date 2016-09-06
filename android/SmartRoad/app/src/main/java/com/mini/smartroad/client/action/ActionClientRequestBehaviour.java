package com.mini.smartroad.client.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.ActionInDto;

import java.io.IOException;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class ActionClientRequestBehaviour extends BaseDoneBehaviour {

    private ActionInDto actionInDto;
    private boolean redo;

    public ActionClientRequestBehaviour(String userToken, String stationToken,
                                        ActionType actionType, Boolean value, boolean redo) {
        actionInDto = new ActionInDto(stationToken, userToken, value, actionType);
        this.redo = redo;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.ACTION_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(actionInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_ACTION);
        if (actionInDto.getActionType() == ActionType.LIKE && !redo && Boolean.TRUE.equals(actionInDto.getValue())) {
            message.setProtocol(Utils.PROTOCOL_LIKE);
        } else if (actionInDto.getActionType() == ActionType.LIKE && redo && Boolean.TRUE.equals(actionInDto.getValue())) {
            message.setProtocol(Utils.PROTOCOL_RELIKE);
        } else if (actionInDto.getActionType() == ActionType.LIKE && Boolean.FALSE.equals(actionInDto.getValue())) {
            message.setProtocol(Utils.PROTOCOL_UNLIKE);
        } else if (actionInDto.getActionType() == ActionType.CONFIRM && Boolean.TRUE.equals(actionInDto.getValue())) {
            message.setProtocol(Utils.PROTOCOL_CONFIRM);
        } else {
            message.setProtocol(Utils.PROTOCOL_UNCONFIRM);
        }
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new ActionClientResponseBehaviour());
    }
}
