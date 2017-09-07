package com.mini.smartroad.service.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.service.track.WaitForDriverTrackBehaviour;

public class ActionAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForDriverTrackBehaviour());
    }
}
