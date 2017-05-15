package com.mini.smartroad.client.driver.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.BaseInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


public class DriverNegotiateGasStationClientRequestBehaviour extends BaseDoneBehaviour {

    private BaseInDto baseInDto;
    private String gasStationAgentName;

    public DriverNegotiateGasStationClientRequestBehaviour(String userToken, String gasStationAgentName) {
        BaseInDto baseInDto = new BaseInDto(userToken);
        this.gasStationAgentName = gasStationAgentName;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(gasStationAgentName, AID.ISLOCALNAME));
        try {
            message.setContentObject(baseInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_NEGOTIATE);
        message.setProtocol(Utils.PROTOCOL_START_NEGOTIATION);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverNegotiateGasStationClientResponseBehaviour());
        isDone = true;
    }
}
