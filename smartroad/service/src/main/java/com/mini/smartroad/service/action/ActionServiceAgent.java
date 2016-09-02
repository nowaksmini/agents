package com.mini.smartroad.service.action;

import com.mini.smartroad.service.base.BaseAgent;
import com.mini.smartroad.service.station.WaitForStationBehaviour;

public class ActionServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForActionBehaviour());
    }
}