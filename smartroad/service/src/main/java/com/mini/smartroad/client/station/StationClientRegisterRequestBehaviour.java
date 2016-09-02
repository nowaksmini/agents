package com.mini.smartroad.client.station;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.StationRegisterInDto;
import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.base.BaseBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class StationClientRegisterRequestBehaviour extends BaseBehaviour {

    private boolean sent;
    private StationRegisterInDto stationRegisterInDto;

    public StationClientRegisterRequestBehaviour(StationRegisterInDto stationRegisterInDto) {
        this.stationRegisterInDto = stationRegisterInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.STATION_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(stationRegisterInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_STATION);
        message.setProtocol(Utils.PROTOCOL_REGISTER);
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
        myAgent.addBehaviour(new StationClientRegisterResponseBehaviour());
    }

    @Override
    public boolean done() {
        return sent;
    }

}
