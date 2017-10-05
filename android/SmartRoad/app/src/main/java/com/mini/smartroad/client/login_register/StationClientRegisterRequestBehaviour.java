package com.mini.smartroad.client.login_register;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class StationClientRegisterRequestBehaviour extends BaseDoneBehaviour {

    private StationRegisterInDto stationRegisterInDto;

    public StationClientRegisterRequestBehaviour(StationRegisterInDto stationRegisterInDto) {
        this.stationRegisterInDto = stationRegisterInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.LOGIN_REGISTER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(stationRegisterInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_STATION);
        message.setProtocol(Utils.PROTOCOL_REGISTER);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new StationClientRegisterResponseBehaviour());
    }
}
