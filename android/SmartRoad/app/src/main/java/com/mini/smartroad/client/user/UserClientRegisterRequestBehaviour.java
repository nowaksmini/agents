package com.mini.smartroad.client.user;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.UserRegisterInDto;

import java.io.IOException;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class UserClientRegisterRequestBehaviour extends BaseDoneBehaviour {

    private UserRegisterInDto userRegisterInDto;

    public UserClientRegisterRequestBehaviour(String email, String firstName, String lastName, String password) {
        userRegisterInDto = new UserRegisterInDto(email, firstName, lastName, password);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.USER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(userRegisterInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_REGISTER);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new UserClientRegisterResponseBehaviour());
    }
}
