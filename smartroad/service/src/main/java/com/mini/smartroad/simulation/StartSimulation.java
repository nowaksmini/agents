package com.mini.smartroad.simulation;

import com.mini.smartroad.Main;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.service.UserSimulatorAgent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class StartSimulation {
    public static void start() {
        try {
            AgentController agentController = Main.getAgentContainer().createNewAgent(UserSimulatorAgent.class.getName() + Utils.USER_SIMULATOR_ID,
                    UserSimulatorAgent.class.getName(), new String[]{"sylwia", "sylwia"});
            agentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
