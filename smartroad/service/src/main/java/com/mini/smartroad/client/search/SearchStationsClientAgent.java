package com.mini.smartroad.client.search;

import com.mini.smartroad.common.ArgumentProperties;
import com.mini.smartroad.base.BaseAgent;

public class SearchStationsClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            if (arguments.length == ArgumentProperties.SEARCH_STATIONS_WEB || arguments.length == ArgumentProperties.SERACH_STATIONS_MOBILE) {
                String userToken = (String) arguments[arguments.length - 4];
                double longitude = (Double) arguments[arguments.length - 3];
                double latitude = (Double) arguments[arguments.length - 2];
                Long distance = (Long) arguments[arguments.length - 1];
                logger.info("Passed arguments: search userToken:" + userToken + ", latitude: " + latitude + ", longitude: "
                        + longitude + ", distance" + distance);
                addBehaviour(new SearchStationsClientRequestBehaviour(userToken, longitude, latitude, distance));
            }
        }
    }
}