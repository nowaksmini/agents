package com.mini.smartroad.service.helper;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseBehaviour;
import com.mini.smartroad.base.BaseInteractBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class WaitForDriverFindBehaviour extends BaseBehaviour {
    private boolean isDone = false;
    private BaseInteractBehaviour nextBehaviour;

    public WaitForDriverFindBehaviour() {
    }

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_FIND_STATIONS)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new DriverServiceFindStationsBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
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
            myAgent.addBehaviour(new WaitForDriverFindBehaviour());
            return true;
        }
        return false;
    }
}
