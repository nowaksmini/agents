package com.mini.smartroad.client.action;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.ActionInDto;

public class StationActionClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case STATION_CONFIRM_USER_CAME:
                    ActionInDto actionInDto = (ActionInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + actionInDto);
                    addBehaviour(new StationClientInformServiceUserCameRequestBehaviour(actionInDto));
                    break;
            }
        }
    }
}
