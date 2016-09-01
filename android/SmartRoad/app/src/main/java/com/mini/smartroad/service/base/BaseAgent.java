package com.mini.smartroad.service.base;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.logging.Logger;

public class BaseAgent extends Agent {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    protected void setup() {
        Logger.getLogger(this.getClass().getName()).info(getLocalName() + " started");
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
