package com.mini.smartroad.service.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseBehaviour;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.Utils;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class WaitForStationConfigurationBehaviour extends BaseBehaviour {
    private boolean isDone = false;
    private BaseInteractBehaviour nextBehaviour;

    public WaitForStationConfigurationBehaviour() {
    }

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_STATION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_GET_PREFERENCES)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new StationServiceGetPreferencesBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_UPDATE_PREFERENCES)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new StationServiceUpdatePreferencesBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_GET_NEGOTIATION_STRATEGY)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new StationServiceGetStrategyBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                isDone = true;
            } else if (msg.getProtocol().equals(Utils.PROTOCOL_UPDATE_NEGOTIATION_STRATEGY)) {
                try {
                    Serializable contentObject = msg.getContentObject();
                    nextBehaviour = new StationServiceUpdateStrategyBehaviour(msg.getSender(), msg.getOntology(), msg.getProtocol() + Utils.SUFFIX_RESPONSE, contentObject);
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
            myAgent.addBehaviour(new WaitForStationConfigurationBehaviour());
            return true;
        }
        return false;
    }
}
