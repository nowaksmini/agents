package com.mini.smartroad.base;

public class BaseStopAgentBehaviour extends BaseBehaviour {

    protected boolean isDone;

    @Override
    public boolean done() {
        if (isDone) {
            myAgent.doDelete();
        }
        return isDone;
    }
}
