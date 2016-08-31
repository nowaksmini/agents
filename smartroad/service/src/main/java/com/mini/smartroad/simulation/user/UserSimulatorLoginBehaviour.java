package com.mini.smartroad.simulation.user;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.UserLoginInDto;
import com.mini.smartroad.service.user.UserServiceAgent;
import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.base.BaseBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class UserSimulatorLoginBehaviour extends BaseBehaviour {

    private boolean sent;
    private UserLoginInDto userLoginInDto;

    public UserSimulatorLoginBehaviour(String login, String password) {
        userLoginInDto = new UserLoginInDto(login, CryptoUtils.encodePassword(password));
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(UserServiceAgent.class.getName(), AID.ISLOCALNAME));
        try {
            message.setContentObject(userLoginInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_LOGIN);
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
        myAgent.addBehaviour(new UserSimulatorLoginResponseBehaviour());
    }

    @Override
    public boolean done() {
        return sent;
    }

}
