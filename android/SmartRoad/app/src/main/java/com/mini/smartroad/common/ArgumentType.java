package com.mini.smartroad.common;

public enum ArgumentType {
    USER_LOGIN,
    USER_REGISTER,

    STATION_LOGIN,
    STATION_REGISTER,

    USER_GET_PREFERENCES, // input BaseInDto
    USER_UPDATE_PREFERENCES,

    STATION_GET_PREFERENCES, // input BaseInDto
    STATION_UPDATE_PREFERENCES,

    STATION_GET_NEGOTIATION_STRATEGY, // input BaseInDto
    STATION_UPDATE_NEGOTIATION_STRATEGY,
    STATION_CONFIRM_USER_CAME,

    USER_POSITION_TRACK,

    USER_FIND_STATIONS,
    USER_FIND_USERS,
    USER_BECOME_REPRESENTATIVE,
    USER_MAKE_GROUP,
    USER_NEGOTIATE_WITH_USERS,
    USER_ACCEPT_INVITATION,
    USER_REJECT_INVITATION
}