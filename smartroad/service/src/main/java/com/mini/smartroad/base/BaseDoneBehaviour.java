package com.mini.smartroad.base;

public class BaseDoneBehaviour extends BaseBehaviour {

    protected boolean isDone;

    @Override
    public boolean done() {
        return isDone;
    }
}