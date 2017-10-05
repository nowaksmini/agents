package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;

public class StationNegotiateClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new StationClientWaitForDriverBecomeRepresentativeBehaviour());
    }
}
