package com.mini.smartroad.client.track;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.track.UserTrackInDto;

public class TrackClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_POSITION_TRACK:
                    UserTrackInDto userTrackInDto = (UserTrackInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + userTrackInDto);
                    addBehaviour(new DriverClientTrackRequestBehaviour(userTrackInDto));
                    break;
            }
        }
    }
}
