package com.mini.smartroad.service;

public class UserServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForUserBehaviour());
    }
}
