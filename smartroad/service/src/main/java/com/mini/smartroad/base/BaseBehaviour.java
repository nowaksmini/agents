package com.mini.smartroad.base;

import jade.core.behaviours.SimpleBehaviour;

import java.util.logging.Logger;

public class BaseBehaviour extends SimpleBehaviour {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void action() {
        logger.info(getClass().getName()+ " action for agent " + getAgent().getName());
    }

    @Override
    public boolean done() {
        return false;
    }
}
