package com.mini.smartroad.service.xyz.action;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseBehaviour;
import com.mini.smartroad.base.BaseInteractBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class WaitForActionBehaviour extends BaseBehaviour {
    private boolean isDone = false;
    private BaseInteractBehaviour nextBehaviour;

    public WaitForActionBehaviour() {
    }

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_ACTION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LIKE)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new ActionServiceLikeBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_UNLIKE)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new ActionServiceUnLikeBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_CONFIRM)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new ActionServiceConfirmBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_UNCONFIRM)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new ActionServiceUnConfirmBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
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
            myAgent.addBehaviour(new WaitForActionBehaviour());
            return true;
        }
        return false;
    }
}
