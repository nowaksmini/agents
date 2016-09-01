package com.mini.smartroad.client.user;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.UserRegisterInDto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.base.BaseBehaviour;

import java.io.IOException;

public class UserClientRegisterRequestBehaviour extends BaseBehaviour {

    private boolean sent;
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
        sent = true;
        myAgent.addBehaviour(new UserClientRegisterResponseBehaviour());
    }

    @Override
    public boolean done() {
        return sent;
    }

}
