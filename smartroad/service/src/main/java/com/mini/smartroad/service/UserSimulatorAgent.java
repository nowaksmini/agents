package com.mini.smartroad.service;

public class UserSimulatorAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length == 2) {
            String login = (String) arguments[0];
            String password = (String) arguments[1];
            logger.info("Passed arguments: login:" + login + " password:" + password);
            addBehaviour(new UserSimulatorLoginBehaviour(login, password));
        }
    }
}
