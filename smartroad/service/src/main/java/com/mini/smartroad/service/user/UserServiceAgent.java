package com.mini.smartroad.service.user;

import com.mini.smartroad.service.base.BaseAgent;

public class UserServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForUserBehaviour());
    }
}