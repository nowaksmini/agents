package com.mini.smartroad;

import com.mini.smartroad.simulation.Simulation;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.service.login_register.LoginRegisterAgent;
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
        startMainContainer();
    }

    private static void startMainContainer() {
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
            AgentController agentLoginRegister = agentContainer.createNewAgent(LoginRegisterAgent.class.getName(), LoginRegisterAgent.class.getName(), null);
            agentLoginRegister.start();
//            AgentController agentStationController = agentContainer.createNewAgent(StationServiceAgent.class.getName(), StationServiceAgent.class.getName(), null);
//            agentStationController.start();
//            AgentController agentSearchController = agentContainer.createNewAgent(HelperServiceAgent.class.getName(), HelperServiceAgent.class.getName(), null);
//            agentSearchController.start();
//            AgentController agentActionController = agentContainer.createNewAgent(ActionServiceAgent.class.getName(), ActionServiceAgent.class.getName(), null);
//            agentActionController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        Simulation.start();
        runtime.shutDown();
    }

    public static AgentContainer getAgentContainer() {
        return agentContainer;
    }
}

