package com.mini.smartroad.client.driver.login;


import com.mini.smartroad.common.ArgumentProperties;
import com.mini.smartroad.base.BaseAgent;

public class DriverClientLoginRegisterAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            if (arguments.length == ArgumentProperties.USER_LOGIN_WEB || arguments.length == ArgumentProperties.USER_LOGIN_MOBILE) {
                String login = (String) arguments[arguments.length - 2];
                String password = (String) arguments[arguments.length - 1];
                logger.info("Passed arguments: login:" + login + " password:" + password);
                addBehaviour(new DriverClientLoginRequestBehaviour(login, password));
            } else if (arguments.length == ArgumentProperties.USER_REGISTER_WEB || arguments.length == ArgumentProperties.USER_REGISTER_MOBILE) {
                String email = (String) arguments[arguments.length - 4];
                String firstName = (String) arguments[arguments.length - 3];
                String lastName = (String) arguments[arguments.length - 2];
                String password = (String) arguments[arguments.length - 1];
                logger.info("Passed arguments: email:" + email + " firstName: " + firstName +
                        " lastName: " + lastName + " password:" + password);
                addBehaviour(new DriverClientRegisterRequestBehaviour(email, firstName, lastName, password));
            }
        }
    }
}
