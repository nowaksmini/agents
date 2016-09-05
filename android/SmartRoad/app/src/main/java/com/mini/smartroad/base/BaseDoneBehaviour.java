package com.mini.smartroad.base;

public class BaseDoneBehaviour extends BaseBehaviour {

    protected boolean sent;

    @Override
    public boolean done() {
        return sent;
    }
}
