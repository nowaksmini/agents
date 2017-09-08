package com.mini.smartroad.common;

public class Utils {
    public static final String ONTOLOGY_USER = "driver";
    public static final String ONTOLOGY_STATION = "station";

    public static final String PROTOCOL_LOGIN = "login";
    public static final String PROTOCOL_REGISTER = "register";

    public static final String PROTOCOL_GET_PREFERENCES = "get_preferences";
    public static final String PROTOCOL_UPDATE_PREFERENCES = "update_preferences";
    public static final String PROTOCOL_GET_NEGOTIATION_STRATEGY = "get_strategy";
    public static final String PROTOCOL_UPDATE_NEGOTIATION_STRATEGY = "update_strategy";

    public static final String PROTOCOL_TRACK = "track";
    public static final String PROTOCOL_FIND_STATIONS = "find_stations";
    public static final String PROTOCOL_BECOME_REPRESENTATIVE = "become_representative";
    public static final String PROTOCOL_MAKE_GROUP = "make_group";
    public static final String PROTOCOL_FIND_USERS = "find_users";
    public static final String PROTOCOL_NEGOTIATE = "negotiate";
    public static final String PROTOCOL_USER_ACCEPT_INVITATION = "user_accept_invitation";
    public static final String PROTOCOL_USER_REJECT_INVITATION = "user_reject_invitation";
    public static final String PROTOCOL_USER_CAME_TO_STATION = "user_came_to_station";

    public static final String SUFFIX_RESPONSE = "-response";

    public static final String IP = "192.168.56.1";
    public static final String PORT = "1099";

    public static final String LOGIN_REGISTER_SERVICE_AGENT = "com.mini.smartroad.service.login_register.LoginRegisterAgent";
    public static final String CONFIGURE_SERVICE_AGENT = "com.mini.smartroad.service.configuration.ConfigurationAgent";
    public static final String TRACKER_SERVICE_AGENT = "com.mini.smartroad.service.track.TrackerAgent";
    public static final String HELPER_SERVICE_AGENT = "com.mini.smartroad.service.helper.HelperAgent";
    public static final String ACTION_SERVICE_AGENT = "com.mini.smartroad.service.action.ActionAgent";
    // TODO, ACTION

    public static final int LIKE_TIME_DURATION = 1; // in hours

    public static final double MIN_LATITUDE = -90;
    public static final double MAX_LATITUDE = 90;
    public static final double MIN_LONGITUDE = -180;
    public static final double MAX_LONGITUDE = 180;

    public static final double KILOMETER_TO_DEGREES_LATITUDE = 0.005;
    public static final double KILOMETER_TO_DEGREES_LONGITUDE = 0.01;
}
