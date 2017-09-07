package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.negotiate.FindUsersInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientFindUsersRequestBehaviour extends BaseDoneBehaviour {

    private FindUsersInDto findUsersInDto;

    public DriverClientFindUsersRequestBehaviour(FindUsersInDto findUsersInDto) {
        this.findUsersInDto = findUsersInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.HELPER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(findUsersInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_FIND_USERS);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverClientFindUsersResponseBehaviour());
        isDone = true;
    }
}
