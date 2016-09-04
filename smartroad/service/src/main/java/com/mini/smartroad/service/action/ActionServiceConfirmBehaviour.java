package com.mini.smartroad.service.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.Serializable;

public class ActionServiceConfirmBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public ActionServiceConfirmBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        super(receiver, ontology, protocol, inputContent);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.FAILURE);
        message.setOntology(getOntology());
        message.setProtocol(getProtocol());
        message.addReceiver(getReceiver());
        // TODO create new confirm entity
//        try {
//            //message.setContentObject(registerStation((StationRegisterInDto) getInputContent(), message));
//        } catch (IOException e) {
//            message.setContent(e.getMessage());
//            message.setPerformative(ACLMessage.FAILURE);
//            e.printStackTrace();
//        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    @Override
    public boolean done() {
        return sent;
    }

}