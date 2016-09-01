package com.mini.smartroad.service.base;

import jade.core.AID;

import java.io.Serializable;

public class BaseInteractBehaviour extends BaseBehaviour {
    private AID receiver;
    private String ontology;
    private String protocol;
    private Serializable inputContent;

    public BaseInteractBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        this.receiver = receiver;
        this.ontology = ontology;
        this.protocol = protocol;
        this.inputContent = inputContent;
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public boolean done() {
        return false;
    }

    public AID getReceiver() {
        return receiver;
    }

    public String getOntology() {
        return ontology;
    }

    public String getProtocol() {
        return protocol;
    }

    public Serializable getInputContent() {
        return inputContent;
    }
}
