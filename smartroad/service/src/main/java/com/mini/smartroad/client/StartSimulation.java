package com.mini.smartroad.client;

import com.mini.smartroad.Main;
import com.mini.smartroad.client.user.UserClientAgent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class StartSimulation {
    public static void start() {
        try {
            AgentController agentControllerRegister = Main.getAgentContainer().createNewAgent(UserClientAgent.class.getName() + 1,
                    UserClientAgent.class.getName(), new String[]{"test@gmail.com", "test_firstName", "test_lastName", "password"});
            agentControllerRegister.start();
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent(UserClientAgent.class.getName() + 2,
                    UserClientAgent.class.getName(), new String[]{"test@gmail.com", "password"});
            agentControllerLogin.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
