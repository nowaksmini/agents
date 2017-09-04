package com.mini.smartroad.service.login_register;

import com.mini.smartroad.base.BaseAgent;

public class LoginRegisterAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForDriverLoginRegisterBehaviour());
        addBehaviour(new WaitForStationLoginRegisterBehaviour());
    }
}
