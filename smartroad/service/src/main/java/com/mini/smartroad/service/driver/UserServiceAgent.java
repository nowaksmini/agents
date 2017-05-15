package com.mini.smartroad.service.driver;

import com.mini.smartroad.base.BaseAgent;

public class UserServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForUserBehaviour());
    }
}
