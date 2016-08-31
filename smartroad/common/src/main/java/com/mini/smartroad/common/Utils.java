package com.mini.smartroad.common;

import jade.core.AID;

public class Utils {
    public static final int USER_SERVICE_ID = 1;
    public static final int USER_SIMULATOR_ID = 2;
    public static final int ID = 0;

    public static final String ONTOLOGY_USER = "user";

    public static final String PROTOCOL_LOGIN = "login";

    public static final String SUFIX_RESPONSE = "-response";

    public static final String IP = "192.168.0.38";
    public static final String PORT = "1099";
    //to tylko na glownym
    public static final AID[] spammers = {
            new AID("sa0", AID.ISLOCALNAME),
            new AID("sa1", AID.ISLOCALNAME),
    };
    public static final AID[] consumers = {
            new AID("mca0", AID.ISLOCALNAME),
            new AID("mca1", AID.ISLOCALNAME)
    };
}
