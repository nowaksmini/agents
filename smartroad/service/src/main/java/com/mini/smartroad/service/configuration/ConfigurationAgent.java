package com.mini.smartroad.service.configuration;

import com.mini.smartroad.base.BaseAgent;

public class ConfigurationAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForDriverConfigurationBehaviour());
        addBehaviour(new WaitForStationConfigurationBehaviour());
    }
}
