package com.mini.smartroad.client.login_register;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientRegisterRequestBehaviour extends BaseDoneBehaviour {

    private UserRegisterInDto userRegisterInDto;

    public DriverClientRegisterRequestBehaviour(UserRegisterInDto userRegisterInDto) {
        this.userRegisterInDto = userRegisterInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.LOGIN_REGISTER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(userRegisterInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_REGISTER);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new DriverClientRegisterResponseBehaviour());
    }
}
