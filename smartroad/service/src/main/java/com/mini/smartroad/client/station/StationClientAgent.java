package com.mini.smartroad.client.station;


import com.mini.smartroad.dto.in.StationRegisterInDto;
import com.mini.smartroad.base.BaseAgent;

public class StationClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null) {
            for (Object argument : arguments) {
                logger.info("Passed argument: " + argument.toString() + "\n");
                addBehaviour(new StationClientRegisterRequestBehaviour((StationRegisterInDto) argument));
            }
        }
    }
}
