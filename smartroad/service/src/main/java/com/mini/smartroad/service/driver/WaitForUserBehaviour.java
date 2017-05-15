package com.mini.smartroad.service.driver;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseBehaviour;
import com.mini.smartroad.base.BaseInteractBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class WaitForUserBehaviour extends BaseBehaviour {
    private boolean isDone = false;
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
                    nextBehaviour = new UserServiceLoginBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_REGISTER)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new UserServiceRegisterBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
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
