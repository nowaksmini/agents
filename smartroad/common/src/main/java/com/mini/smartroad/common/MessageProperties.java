package com.mini.smartroad.common;

public final class MessageProperties {
    public static final String ERROR_USER_LOGIN = "Wrong username or password.";
    public static final String ERROR_USER_UNIQUE = "Username not unique.";
    public static final String ERROR_USER_ALREADY_DATABASE = "Username already in database. Try login_register.";
    public static final String ERROR_NO_USER_WITH_TOKEN = "There is no driver with token";
    public static final String ERROR_USER_NO_INPUT_TOKEN = "User token is empty.";
    public static final String ERROR_NO_INFORMATION_ABOUT_CURRENT_LOCATION = "No information about current location.";

    public static final String ERROR_STATION_LOGIN = "Wrong username or secret code.";
    public static final String ERROR_STATION_ALREADY_DATABASE = "Station already in database.";
    public static final String ERROR_NO_STATION_WITH_TOKEN = "There is no station with token";
    public static final String ERROR_STATION_UNIQUE = "Station not unique.";

    public static final String ERROR_GROUP_ALREADY_EXISTS = "Group already exists";
}
