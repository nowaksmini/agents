package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.client.action.DriverClientInformServiceInvitationAcceptedRequestBehaviour;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.ActionInDto;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.negotiate.AskForJoiningGroupInDto;
import com.mini.smartroad.dto.in.negotiate.FindStationsInDto;
import com.mini.smartroad.dto.in.negotiate.FindUsersInDto;

public class DriverNegotiateClientAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new DriverClientWaitForRepresentativeAskFoJoiningGroupBehaviour());
        Object[] arguments = getArguments();
        if (arguments != null && arguments.length > 0) {
            ArgumentType argumentType = (ArgumentType) arguments[arguments.length - 1];
            switch (argumentType) {
                case USER_FIND_STATIONS:
                    FindStationsInDto findStationsInDto = (FindStationsInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + findStationsInDto);
                    addBehaviour(new DriverClientFindStationsRequestBehaviour(findStationsInDto));
                    break;
                case USER_BECOME_REPRESENTATIVE:
                    BaseInDto baseInDto = (BaseInDto) arguments[arguments.length - 2];
                    String stationAgentName = (String) arguments[arguments.length - 3];
                    logger.info("Passed arguments: " + baseInDto + ", " + stationAgentName);
                    addBehaviour(new DriverClientBecomeRepresentativeRequestBehaviour(baseInDto, stationAgentName));
                    break;
                case USER_FIND_USERS:
                    FindUsersInDto findUsersInDto = (FindUsersInDto) arguments[arguments.length - 2];
                    logger.info("Passed arguments: " + findUsersInDto);
                    addBehaviour(new DriverClientFindUsersRequestBehaviour(findUsersInDto));
                    break;
                case USER_NEGOTIATE_WITH_USERS:
                    AskForJoiningGroupInDto askForJoiningGroupInDto = (AskForJoiningGroupInDto) arguments[arguments.length - 2];
                    String userAgentName = (String) arguments[arguments.length - 3];
                    logger.info("Passed arguments: " + askForJoiningGroupInDto + ", " + userAgentName);
                    addBehaviour(new DriverClientAskForJoiningGroupRequestBehaviour(askForJoiningGroupInDto,
                            userAgentName));
                    break;
            }
        }
    }
}
