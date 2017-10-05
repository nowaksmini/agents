package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseBehaviour;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.Utils;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class DriverClientWaitForRepresentativeAskFoJoiningGroupBehaviour extends BaseBehaviour {
    private boolean isDone = false;
    private BaseInteractBehaviour nextBehaviour;

    public DriverClientWaitForRepresentativeAskFoJoiningGroupBehaviour() {
    }

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchProtocol(Utils.PROTOCOL_NEGOTIATE));
        if (msg != null) {
            if (msg.getOntology().equals(Utils.ONTOLOGY_USER)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new DriverClientAnswerForInvitationJoiningGroupBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
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
            myAgent.addBehaviour(new DriverClientWaitForRepresentativeAskFoJoiningGroupBehaviour());
            return true;
        }
        return false;
    }
}
