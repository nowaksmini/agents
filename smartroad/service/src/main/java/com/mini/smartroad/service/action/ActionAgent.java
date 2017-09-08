package com.mini.smartroad.service.action;

import com.mini.smartroad.base.BaseAgent;

public class ActionAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForDriverActionBehaviour());
        addBehaviour(new WaitForStationActionBehaviour());
    }
}
