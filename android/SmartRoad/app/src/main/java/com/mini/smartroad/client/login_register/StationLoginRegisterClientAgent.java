package com.mini.smartroad.client.login_register;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.login.StationLoginInDto;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;

public class StationLoginRegisterClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
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
