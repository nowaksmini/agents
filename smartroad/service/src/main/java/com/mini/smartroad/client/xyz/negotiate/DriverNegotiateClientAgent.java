package com.mini.smartroad.client.xyz.negotiate;

import com.mini.smartroad.base.BaseAgent;

/**
 * Request negotiation as representative to petrol station or send
 * information about negotiation to other users.
 */
public class DriverNegotiateClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
//            if (arguments.length == ArgumentsType.SEARCH_STATIONS_WEB || arguments.length == ArgumentsType.SEARCH_STATIONS_MOBILE) {
//                String userToken = (String) arguments[arguments.length - 2];
//                String petrolStationAgentName = (String) arguments[arguments.length - 1];
//                logger.info("Passed arguments: userToken:" + userToken + ", petrolStationAgentName" + petrolStationAgentName);
//                addBehaviour(new DriverNegotiateGasStationClientRequestBehaviour(userToken, petrolStationAgentName));
//            }
        }
    }
}