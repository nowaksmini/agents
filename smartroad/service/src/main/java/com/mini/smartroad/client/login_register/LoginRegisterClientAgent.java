package com.mini.smartroad.client.login_register;


import com.mini.smartroad.common.ArgumentsType;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.dto.in.login.StationLoginInDto;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;

public class LoginRegisterClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentsType argumentsType = (ArgumentsType) arguments[arguments.length - 1];
            switch (argumentsType) {
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
                case STATION_LOGIN:
                    StationLoginInDto stationLoginInDto = (StationLoginInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + stationLoginInDto);
                    addBehaviour(new StationClientLoginRequestBehaviour(stationLoginInDto));
                    break;
                case STATION_REGISTER:
                    StationRegisterInDto stationRegisterInDto = (StationRegisterInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + stationRegisterInDto);
                    addBehaviour(new StationClientRegisterRequestBehaviour(stationRegisterInDto));
                    break;
            }
        }
    }
}
