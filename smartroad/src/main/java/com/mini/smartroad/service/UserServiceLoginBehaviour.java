package com.mini.smartroad.service;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.Serializable;

public class UserServiceLoginBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public UserServiceLoginBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        super(receiver, ontology, protocol, inputContent);
    }


    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        message.setOntology(getOntology());
        message.setProtocol(getProtocol());
        message.addReceiver(getReceiver());
        message.setContent("OK");
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    @Override
    public boolean done() {
        return sent;
    }

}