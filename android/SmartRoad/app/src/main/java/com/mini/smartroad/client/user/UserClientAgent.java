package com.mini.smartroad.client.user;


import com.mini.smartroad.service.base.BaseAgent;

public class UserClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            if (arguments.length == 3) {
                String login = (String) arguments[1];
                String password = (String) arguments[2];
                logger.info("Passed arguments: login:" + login + " password:" + password);
                addBehaviour(new UserClientLoginRequestBehaviour(login, password));
            } else if (arguments.length == 5) {
                String email = (String) arguments[1];
                String firstName = (String) arguments[2];
                String lastName = (String) arguments[3];
                String password = (String) arguments[4];
                logger.info("Passed arguments: email:" + email + " firstName: " + firstName +
                        " lastName: " + lastName + " password:" + password);
                addBehaviour(new UserClientRegisterRequestBehaviour(email, firstName, lastName, password));
            }
        }
    }
}
