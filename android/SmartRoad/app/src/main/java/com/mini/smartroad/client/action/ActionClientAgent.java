package com.mini.smartroad.client.action;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.ArgumentProperties;

public class ActionClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            if (arguments.length == ArgumentProperties.ACTION_MOBILE || arguments.length == ArgumentProperties.ACTION_WEB) {
                String userToken = (String) arguments[arguments.length - 5];
                String stationToken = (String) arguments[arguments.length - 4];
                ActionType actionType = (ActionType) arguments[arguments.length - 3];
                Boolean value = (Boolean) arguments[arguments.length - 2];
                Boolean redo = (Boolean) arguments[arguments.length - 1];
                logger.info("Passed arguments: action userToken:" + userToken + ", stationToken: " + stationToken
                        + ", actionType: " + actionType + ", value" + value);
                addBehaviour(new ActionClientRequestBehaviour(userToken, stationToken, actionType, value, redo));
            }
        }
    }
}
