package com.mini.smartroad;

import com.mini.smartroad.service.action.ActionAgent;
import com.mini.smartroad.service.configuration.ConfigurationAgent;
import com.mini.smartroad.service.helper.HelperAgent;
import com.mini.smartroad.service.track.TrackerAgent;
import com.mini.smartroad.simulation.Simulation;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.service.login_register.LoginRegisterAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.HashMap;
import java.util.List;

public class Main {
    private static jade.core.Runtime runtime = jade.core.Runtime.instance();
    private static jade.wrapper.AgentContainer agentContainer;

    public static HashMap<String, DriverRuntimeInfo> drivers = new HashMap<>();
    public static HashMap<String, GroupRuntimeInfo> groups = new HashMap<>();
    public static HashMap<String, List<String>> currentGroupParticipants = new HashMap<>();

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
            AgentController agentConfiguration = agentContainer.createNewAgent(ConfigurationAgent.class.getName(), ConfigurationAgent.class.getName(), null);
            agentConfiguration.start();
            AgentController trackerAgent = agentContainer.createNewAgent(TrackerAgent.class.getName(), TrackerAgent.class.getName(), null);
            trackerAgent.start();
            AgentController helperServiceAgent = agentContainer.createNewAgent(HelperAgent.class.getName(), HelperAgent.class.getName(), null);
            helperServiceAgent.start();
            AgentController actionServiceAgent = agentContainer.createNewAgent(ActionAgent.class.getName(), ActionAgent.class.getName(), null);
            actionServiceAgent.start();
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

