package com.mini.smartroad.client.action;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.ActionInDto;

public class DriverActionClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_MAKE_GROUP:
                    ActionInDto actionInDto = (ActionInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + actionInDto);
                    addBehaviour(new DriverClientMakeGroupRequestBehaviour(actionInDto));
                case USER_ACCEPT_INVITATION:
                    ActionInDto actionInDto1 = (ActionInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + actionInDto1);
                    addBehaviour(new DriverClientInformServiceInvitationAcceptedRequestBehaviour(actionInDto1));
                case USER_REJECT_INVITATION:
                    ActionInDto actionInDto2 = (ActionInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + actionInDto2);
                    addBehaviour(new DriverClientInformServiceInvitationRejectedRequestBehaviour(actionInDto2));
            }
        }
    }
}
