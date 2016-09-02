package com.mini.smartroad.client.user;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.UserLoginInDto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.base.BaseBehaviour;

import java.io.IOException;

public class UserClientLoginRequestBehaviour extends BaseBehaviour {

    private boolean sent;
    private UserLoginInDto userLoginInDto;

    public UserClientLoginRequestBehaviour(String login, String password) {
        userLoginInDto = new UserLoginInDto(login, CryptoUtils.encodePassword(password));
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.USER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(userLoginInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_LOGIN);
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
        myAgent.addBehaviour(new UserClientLoginResponseBehaviour());
    }

    @Override
    public boolean done() {
        return sent;
    }

}