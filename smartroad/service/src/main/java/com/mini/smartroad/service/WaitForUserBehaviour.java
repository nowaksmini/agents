package com.mini.smartroad.service;

import com.mini.smartroad.common.Utils;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class WaitForUserBehaviour extends BaseBehaviour {
    boolean isDone = false;
    private BaseInteractBehaviour nextBehaviour;

    public WaitForUserBehaviour() {
    }

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LOGIN)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new UserServiceLoginBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        if (isDone) {
            myAgent.addBehaviour(nextBehaviour);
            myAgent.addBehaviour(new WaitForUserBehaviour());
            return true;
        }
        return false;
    }
}
