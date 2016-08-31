package com.mini.smartroad.simulation.user;

import com.mini.smartroad.service.base.BaseAgent;

public class UserSimulatorAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            if (arguments.length == 2) {
                String login = (String) arguments[0];
                String password = (String) arguments[1];
                logger.info("Passed arguments: login:" + login + " password:" + password);
                addBehaviour(new UserSimulatorLoginBehaviour(login, password));
            } else if (arguments.length == 4) {
                String email = (String) arguments[0];
                String firstName = (String) arguments[1];
                String lastName = (String) arguments[2];
                String password = (String) arguments[3];
                logger.info("Passed arguments: email:" + email + " firstName: " + firstName +
                        " lastName: " + lastName + " password:" + password);
                addBehaviour(new UserSimulatorRegisterBehaviour(email, firstName, lastName, password));
            }
        }
    }
}
