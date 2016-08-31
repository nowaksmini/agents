package com.mini.smartroad.simulation;

import com.mini.smartroad.Main;
import com.mini.smartroad.simulation.user.UserSimulatorAgent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class StartSimulation {
    public static void start() {
        try {
            AgentController agentControllerRegister = Main.getAgentContainer().createNewAgent(UserSimulatorAgent.class.getName() + 1,
                    UserSimulatorAgent.class.getName(), new String[]{"test@gmail.com", "test_firstName", "test_lastName", "password"});
            agentControllerRegister.start();
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent(UserSimulatorAgent.class.getName() + 2,
                    UserSimulatorAgent.class.getName(), new String[]{"test@gmail.com", "password"});
            agentControllerLogin.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
