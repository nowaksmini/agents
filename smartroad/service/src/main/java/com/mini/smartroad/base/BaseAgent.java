package com.mini.smartroad.base;

import com.mini.smartroad.service.user.UserServiceAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.logging.Logger;

public class BaseAgent extends Agent {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    protected void setup() {
        Logger.getLogger(UserServiceAgent.class.getName()).info(getLocalName() + " started");
    }

    public void sendMessage(ACLMessage aclMessage) {
        send(aclMessage);
        logger.warning(getName() + " send " + aclMessage);
    }

    public ACLMessage receiveMessage(MessageTemplate messageTemplate) {
        ACLMessage aclMessage = receive(messageTemplate);
        logger.warning(getName() + " receive " + aclMessage);
        return aclMessage;
    }
}
