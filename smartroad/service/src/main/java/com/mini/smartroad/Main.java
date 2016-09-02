package com.mini.smartroad;

import com.mini.smartroad.client.StartSimulation;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.service.station.StationServiceAgent;
import com.mini.smartroad.service.user.UserServiceAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Main {
    private static jade.core.Runtime runtime = jade.core.Runtime.instance();
    private static jade.wrapper.AgentContainer agentContainer;

    public static void main(String[] args) throws StaleProxyException {
        startMainContiner();
    }

    public static void startMainContiner() {
        runtime.setCloseVM(true);
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, Utils.IP);
        profile.setParameter(Profile.MAIN_PORT, Utils.PORT);
        agentContainer = runtime.createMainContainer(profile);
        AgentController rma;
        try {
            rma = agentContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        try {
            AgentController agentUserController = agentContainer.createNewAgent(UserServiceAgent.class.getName(), UserServiceAgent.class.getName(), null);
            agentUserController.start();
            AgentController agentStationController = agentContainer.createNewAgent(StationServiceAgent.class.getName(), StationServiceAgent.class.getName(), null);
            agentStationController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
//        StartSimulation.start();
        runtime.shutDown();
    }

    public static AgentContainer getAgentContainer() {
        return agentContainer;
    }
}

