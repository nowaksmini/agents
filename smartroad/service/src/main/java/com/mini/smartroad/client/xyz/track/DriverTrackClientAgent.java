package com.mini.smartroad.client.xyz.track;

import com.mini.smartroad.base.BaseAgent;

/**
 * Passes current location of driver, receives list of all gas stations in the neighbourhood
 * with their details and software agents names
 */
public class DriverTrackClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
//            if (arguments.length == ArgumentType.SEARCH_STATIONS_WEB || arguments.length == ArgumentType.SEARCH_STATIONS_MOBILE) {
//                String userToken = (String) arguments[arguments.length - 4];
//                double latitude = (Double) arguments[arguments.length - 3];
//                double longitude = (Double) arguments[arguments.length - 2];
//                Long distance = (Long) arguments[arguments.length - 1];
//                logger.info("Passed arguments: helper userToken:" + userToken + ", latitude: " + latitude + ", longitude: "
//                        + longitude + ", distance" + distance);
//                addBehaviour(new DriverTrackClientRequestBehaviour(userToken, latitude, longitude, distance));
//            }
        }
    }
}