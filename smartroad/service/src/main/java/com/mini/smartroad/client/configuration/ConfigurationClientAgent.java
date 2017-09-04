package com.mini.smartroad.client.configuration;


import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.configure.StationNegotiationStrategyInDto;
import com.mini.smartroad.dto.in.configure.StationPreferencesInDto;
import com.mini.smartroad.dto.in.configure.UserPreferencesInDto;

public class ConfigurationClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        BaseInDto baseInDto;
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_GET_PREFERENCES:
                    baseInDto = (BaseInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + baseInDto);
                    addBehaviour(new DriverClientGetPreferencesRequestBehaviour(baseInDto));
                    break;
                case STATION_GET_PREFERENCES:
                    baseInDto = (BaseInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + baseInDto);
                    addBehaviour(new StationClientGetPreferencesRequestBehaviour(baseInDto));
                    break;
                case USER_UPDATE_PREFERENCES:
                    UserPreferencesInDto userPreferencesInDto = (UserPreferencesInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + userPreferencesInDto);
                    addBehaviour(new DriverClientUpdatePreferencesRequestBehaviour(userPreferencesInDto));
                    break;
                case STATION_UPDATE_PREFERENCES:
                    StationPreferencesInDto stationPreferencesInDto = (StationPreferencesInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + stationPreferencesInDto);
                    addBehaviour(new StationClientUpdatePreferencesRequestBehaviour(stationPreferencesInDto));
                    break;
                case STATION_GET_NEGOTIATION_STRATEGY:
                    baseInDto = (BaseInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + baseInDto);
                    addBehaviour(new StationClientGetStrategyRequestBehaviour(baseInDto));
                    break;
                case STATION_UPDATE_NEGOTIATION_STRATEGY:
                    StationNegotiationStrategyInDto stationNegotiationStrategyInDto = (StationNegotiationStrategyInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + stationNegotiationStrategyInDto);
                    addBehaviour(new StationClientUpdateStrategyRequestBehaviour(stationNegotiationStrategyInDto));
                    break;
            }
        }
    }
}
