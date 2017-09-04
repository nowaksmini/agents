package com.mini.smartroad.client.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.configure.StationNegotiationStrategyInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class StationClientUpdateStrategyRequestBehaviour extends BaseDoneBehaviour {

    private StationNegotiationStrategyInDto stationNegotiationStrategyInDto;

    public StationClientUpdateStrategyRequestBehaviour(StationNegotiationStrategyInDto stationNegotiationStrategyInDto) {
        this.stationNegotiationStrategyInDto = stationNegotiationStrategyInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.CONFIGURE_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(stationNegotiationStrategyInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_STATION);
        message.setProtocol(Utils.PROTOCOL_UPDATE_NEGOTIATION_STRATEGY);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new StationClientUpdateStrategyResponseBehaviour());
    }
}
