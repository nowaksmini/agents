package com.mini.smartroad.client.xyz.action;


import com.mini.smartroad.base.BaseAgent;

public class ActionClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
//        if (arguments != null) {
//            if (arguments.length == ArgumentsType.ACTION_MOBILE || arguments.length == ArgumentsType.ACTION_WEB) {
//                String userToken = (String) arguments[arguments.length - 4];
//                String stationToken = (String) arguments[arguments.length - 3];
//                ActionType actionType = (ActionType) arguments[arguments.length - 2];
//                Boolean value = (Boolean) arguments[arguments.length - 1];
//                logger.info("Passed arguments: action userToken:" + userToken + ", stationToken: " + stationToken
//                        + ", actionType: " + actionType + ", value" + value);
//                addBehaviour(new ActionClientRequestBehaviour(userToken, stationToken, actionType, value));
//            }
//        }
    }
}
