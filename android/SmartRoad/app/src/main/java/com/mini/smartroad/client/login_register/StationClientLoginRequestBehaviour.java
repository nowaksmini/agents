package com.mini.smartroad.client.login_register;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.login.StationLoginInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class StationClientLoginRequestBehaviour extends BaseDoneBehaviour {

    private StationLoginInDto stationLoginInDto;

    public StationClientLoginRequestBehaviour(StationLoginInDto stationLoginInDto) {
        this.stationLoginInDto = stationLoginInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.LOGIN_REGISTER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(stationLoginInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_STATION);
        message.setProtocol(Utils.PROTOCOL_LOGIN);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new StationClientLoginResponseBehaviour());
    }
}
