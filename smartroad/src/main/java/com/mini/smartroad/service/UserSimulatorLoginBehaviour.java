package com.mini.smartroad.service;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.UserLoginDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class UserSimulatorLoginBehaviour extends BaseBehaviour {

    private boolean sent;
    private UserLoginDto userLoginDto;

    public UserSimulatorLoginBehaviour(String login, String password) {
        userLoginDto = new UserLoginDto(login, CryptoUtils.encodePassword(password));
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(UserServiceAgent.class.getName() + Utils.USER_SERVICE_ID, AID.ISLOCALNAME));
        try {
            message.setContentObject(userLoginDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_LOGIN);
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    @Override
    public boolean done() {
        return sent;
    }

}
