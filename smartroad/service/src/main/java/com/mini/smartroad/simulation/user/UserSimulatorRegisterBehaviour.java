package com.mini.smartroad.simulation.user;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.UserRegisterInDto;
import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.base.BaseBehaviour;
import com.mini.smartroad.service.user.UserServiceAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class UserSimulatorRegisterBehaviour extends BaseBehaviour {

    private boolean sent;
    private UserRegisterInDto userRegisterInDto;

    public UserSimulatorRegisterBehaviour(String email, String firstName, String lastName, String password) {
        userRegisterInDto = new UserRegisterInDto(email, firstName, lastName, CryptoUtils.encodePassword(password));
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(UserServiceAgent.class.getName(), AID.ISLOCALNAME));
        try {
            message.setContentObject(userRegisterInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_REGISTER);
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
        myAgent.addBehaviour(new UserSimulatorRegisterResponseBehaviour());
    }

    @Override
    public boolean done() {
        return sent;
    }

}
