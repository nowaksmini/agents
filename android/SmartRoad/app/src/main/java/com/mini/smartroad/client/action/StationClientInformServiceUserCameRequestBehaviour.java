package com.mini.smartroad.client.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.ActionInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class StationClientInformServiceUserCameRequestBehaviour extends BaseDoneBehaviour {

    private ActionInDto actionInDto;

    public StationClientInformServiceUserCameRequestBehaviour(ActionInDto actionInDto){
        this.actionInDto = actionInDto;
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
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_USER_CAME_TO_STATION);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverClientInformServiceUserCameResponseBehaviour());
        isDone = true;
    }
}
