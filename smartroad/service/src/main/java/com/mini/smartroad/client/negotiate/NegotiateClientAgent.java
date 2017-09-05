package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.negotiate.FindStationsInDto;

public class NegotiateClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_FIND_STATIONS:
                    FindStationsInDto findStationsInDto = (FindStationsInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + findStationsInDto);
                    addBehaviour(new DriverClientFindStationsRequestBehaviour(findStationsInDto));
                    break;
            }
        }
    }
}
