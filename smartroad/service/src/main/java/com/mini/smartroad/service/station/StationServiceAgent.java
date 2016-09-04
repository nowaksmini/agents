package com.mini.smartroad.service.station;

import com.mini.smartroad.base.BaseAgent;

public class StationServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForStationBehaviour());
    }
}