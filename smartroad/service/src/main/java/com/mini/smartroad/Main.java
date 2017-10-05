package com.mini.smartroad;

import com.mini.smartroad.common.Utils;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.StationStrategyEntity;
import com.mini.smartroad.model.UserEntity;
import com.mini.smartroad.service.action.ActionAgent;
import com.mini.smartroad.service.configuration.ConfigurationAgent;
import com.mini.smartroad.service.helper.HelperAgent;
import com.mini.smartroad.service.login_register.LoginRegisterAgent;
import com.mini.smartroad.service.track.TrackerAgent;
import com.mini.smartroad.simulation.Simulation;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {
    private static jade.core.Runtime runtime = jade.core.Runtime.instance();
    private static jade.wrapper.AgentContainer agentContainer;

    public static HashMap<String, DriverRuntimeInfo> drivers = new HashMap<>();
    public static HashMap<String, GroupRuntimeInfo> groups = new HashMap<>();
    public static HashMap<String, List<String>> currentGroupParticipants = new HashMap<>();

    public static void main(String[] args) throws StaleProxyException {
        startMainContainer();
    }

    public static void updateGroups(String stationToken) {
        GroupRuntimeInfo groupRuntimeInfo = groups.get(stationToken);
        groupRuntimeInfo.setCurrentNumberOfCars(groupRuntimeInfo.getCurrentNumberOfCars() + 1);
        int currentNumberOfCars = groupRuntimeInfo.getCurrentNumberOfCars();
        Session session = HibernateUtils.getSessionFactory().openSession();
        List foundStations = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", stationToken)).list();
        StationEntity stationEntity = (StationEntity) foundStations.get(0);
        StationStrategyEntity stationStrategy = stationEntity.getStationStrategy();
        Integer points = groupRuntimeInfo.getCurrentReward();
        if (currentNumberOfCars >= stationStrategy.getGroupSize0()) {
            if (stationStrategy.getPointsGroupSize0() >= points) {
                points = stationStrategy.getPointsGroupSize0();
            }
        }
        if (currentNumberOfCars >= stationStrategy.getGroupSize1()) {
            if (stationStrategy.getPointsGroupSize1() >= points) {
                points = stationStrategy.getPointsGroupSize1();
            }
        }
        if (currentNumberOfCars >= stationStrategy.getGroupSize2()) {
            if (stationStrategy.getPointsGroupSize2() >= points) {
                points = stationStrategy.getPointsGroupSize2();
            }
        }
        if (currentNumberOfCars >= stationStrategy.getGroupSize3()) {
            if (stationStrategy.getPointsGroupSize3() >= points) {
                points = stationStrategy.getPointsGroupSize3();
            }
        }
        if (currentNumberOfCars >= stationStrategy.getGroupSize4()) {
            if (stationStrategy.getPointsGroupSize4() >= points) {
                points = stationStrategy.getPointsGroupSize4();
            }
        }
        groupRuntimeInfo.setCurrentReward(points);
        groups.put(stationToken, groupRuntimeInfo);
        session.close();
    }

    public static void giveRewards() {
        System.out.println("------------------------ REWARD DRIVERS START ------------------------");
        Set<String> groupTokens = currentGroupParticipants.keySet();
        Session session = HibernateUtils.getSessionFactory().openSession();
        for (String groupToken : groupTokens) {
            GroupRuntimeInfo groupRuntimeInfo = groups.get(groupToken);
            int currentReward = groupRuntimeInfo.getCurrentReward();
            List<String> participants = currentGroupParticipants.get(groupToken);
            for (int i = 0; i < participants.size(); i++) {
                String userToken = participants.get(i);
                List foundUsers = session.createCriteria(UserEntity.class)
                        .add(Restrictions.eq("token", userToken)).list();
                UserEntity userEntity = (UserEntity) foundUsers.get(0);
                if (i == 0) {
                    userEntity.setPoints((int) (currentReward * 1.2d));
                    System.out.println("reward Driver: " + userEntity.getEmail() + " with points: " + (int) (currentReward * 1.2d));
                } else {
                    userEntity.setPoints(currentReward);
                    System.out.println("reward Driver: " + userEntity.getEmail() + " with points: " + currentReward);
                }
                session.save(userEntity);
            }
        }
        session.close();
        System.out.println("------------------------ REWARD DRIVERS END ------------------------");
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
        //Simulation.start();
        runtime.shutDown();
    }

    public static AgentContainer getAgentContainer() {
        return agentContainer;
    }
}

