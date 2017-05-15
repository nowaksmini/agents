package com.mini.smartroad.client.driver.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.FindDriversInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


public class DriverNegotiateGetUsersClientRequestBehaviour extends BaseDoneBehaviour {

    private FindDriversInDto findDriversInDto;

    public DriverNegotiateGetUsersClientRequestBehaviour(String userToken, String stationToken) {
        findDriversInDto = new FindDriversInDto(userToken, stationToken);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.HELPER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(findDriversInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_NEGOTIATE);
        message.setProtocol(Utils.PROTOCOL_FIND_USERS);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverNegotiateGetUsersClientResponseBehaviour());
        isDone = true;
    }
}
