package com.mini.smartroad.service.track;

import com.mini.smartroad.base.BaseAgent;

public class TrackerAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForDriverTrackBehaviour());
    }
}
