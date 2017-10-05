package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.negotiate.AskForJoiningGroupInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientAskForJoiningGroupRequestBehaviour extends BaseDoneBehaviour {

    private AskForJoiningGroupInDto askForJoiningGroupInDto;
    private String userAgentName;

    public DriverClientAskForJoiningGroupRequestBehaviour(AskForJoiningGroupInDto askForJoiningGroupInDto, String userAgentName) {
        this.askForJoiningGroupInDto = askForJoiningGroupInDto;
        this.userAgentName = userAgentName;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(userAgentName, AID.ISLOCALNAME));
        try {
            message.setContentObject(askForJoiningGroupInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_NEGOTIATE);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverClientAskForJoiningGroupResponseBehaviour());
        isDone = true;
    }
}
