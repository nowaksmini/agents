package com.mini.smartroad.common;

public class Utils {
    public static final String ONTOLOGY_USER = "driver";
    public static final String ONTOLOGY_STATION = "station";
    public static final String ONTOLOGY_TRACK = "track";
    public static final String ONTOLOGY_ACTION = "action";
    public static final String ONTOLOGY_NEGOTIATE = "negotiate";

    public static final String PROTOCOL_LOGIN = "login";
    public static final String PROTOCOL_REGISTER = "register";
    public static final String PROTOCOL_FIND_STATIONS = "find_stations";
    public static final String PROTOCOL_LIKE = "like";
    public static final String PROTOCOL_UNLIKE = "unlike";
    public static final String PROTOCOL_CONFIRM = "confirm";
    public static final String PROTOCOL_UNCONFIRM = "unconfirm";
    public static final String PROTOCOL_START_NEGOTIATION = "start_negotiation";
    public static final String PROTOCOL_FIND_USERS = "find_users";
    public static final String PROTOCOL_MAKE_GROUP = "make_group";


    public static final String SUFFIX_RESPONSE = "-response";

    public static final String IP = "192.168.56.1";
    public static final String PORT = "1099";

    public static final String LOGIN_REGISTER_SERVICE_AGENT = "com.mini.smartroad.service.login_register.LoginRegisterAgent";

    public static final String HELPER_SERVICE_AGENT = "com.mini.smartroad.service.xyz.helper.SearchServiceAgent";
    public static final String ACTION_SERVICE_AGENT = "com.mini.smartroad.service.xyz.action.ActionServiceAgent";

    public static final int LIKE_TIME_DURATION = 1; // in hours
    public static final int COUPON_TIME_DURATION = 1; // in hours

    public static final double MIN_LATITUDE = -90;
    public static final double MAX_LATITUDE = 90;
    public static final double MIN_LONGITUDE = -180;
    public static final double MAX_LONGITUDE = 180;

    public static final double KILOMETER_TO_DEGREES_LATITUDE = 0.005;
    public static final double KILOMETER_TO_DEGREES_LONGITUDE = 0.01;
}
