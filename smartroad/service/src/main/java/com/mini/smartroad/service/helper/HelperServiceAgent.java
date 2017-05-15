package com.mini.smartroad.service.helper;

import com.mini.smartroad.base.BaseAgent;

public class HelperServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForSearchBehaviour());
    }
}