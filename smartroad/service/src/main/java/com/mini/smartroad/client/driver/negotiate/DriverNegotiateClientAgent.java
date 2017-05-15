package com.mini.smartroad.client.driver.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentProperties;

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
            if (arguments.length == ArgumentProperties.SEARCH_STATIONS_WEB || arguments.length == ArgumentProperties.SEARCH_STATIONS_MOBILE) {
                String userToken = (String) arguments[arguments.length - 2];
                String petrolStationAgentName = (String) arguments[arguments.length - 1];
                logger.info("Passed arguments: userToken:" + userToken + ", petrolStationAgentName" + petrolStationAgentName);
                addBehaviour(new DriverNegotiateGasStationClientRequestBehaviour(userToken, petrolStationAgentName));
            }
        }
    }
}