package com.mini.smartroad.client.login_register;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;

public class DriverLoginRegisterClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_LOGIN:
                    UserLoginInDto userLoginInDto = (UserLoginInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + userLoginInDto);
                    addBehaviour(new DriverClientLoginRequestBehaviour(userLoginInDto));
                    break;
                case USER_REGISTER:
                    UserRegisterInDto userRegisterInDto = (UserRegisterInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + userRegisterInDto);
                    addBehaviour(new DriverClientRegisterRequestBehaviour(userRegisterInDto));
                    break;
            }
        }
    }
}
