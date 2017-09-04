package com.mini.smartroad.simulation;

import com.mini.smartroad.Main;
import com.mini.smartroad.client.configuration.ConfigurationClientAgent;
import com.mini.smartroad.client.login_register.LoginRegisterClientAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.configure.UserPreferencesInDto;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DriverSimulation {
    public void start() {
        try {
            simulateRegisterUser();
            Thread.sleep(3000);
            simulateLoginUser();
            Thread.sleep(10000);
            simulateUpdateUserPreferences();
            Thread.sleep(5000);
            simulateGetUserPreferences();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateRegisterUser() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_register.xml");
        NodeList userRegisterInDto = document.getElementsByTagName("UserRegisterInDto");
        for (int i = 0; i < userRegisterInDto.getLength(); i++) {
            Node item = userRegisterInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<String>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String email = params.get(0);
            String firstName = params.get(1);
            String lastName = params.get(2);
            String password = params.get(3);
            AgentController agentControllerRegister = Main.getAgentContainer().createNewAgent
                    (LoginRegisterClientAgent.class.getName() + (1 + 4 * i),
                            LoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new UserRegisterInDto(email, firstName, lastName, password),
                                    ArgumentType.USER_REGISTER
                            }
                    );
            agentControllerRegister.start();
        }
    }

    private void simulateLoginUser() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_login.xml");
        NodeList userRegisterInDto = document.getElementsByTagName("UserLoginInDto");
        for (int i = 0; i < userRegisterInDto.getLength(); i++) {
            Node item = userRegisterInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<String>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String login = params.get(0);
            String password = params.get(1);
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent
                    (LoginRegisterClientAgent.class.getName() + (2 + 4 * i),
                            LoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new UserLoginInDto(login, password),
                                    ArgumentType.USER_LOGIN
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateGetUserPreferences() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_get_preferences.xml");
        NodeList baseInDto = document.getElementsByTagName("BaseInDto");
        for (int i = 0; i < baseInDto.getLength(); i++) {
            Node item = baseInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<String>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String token = params.get(0);
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent
                    (ConfigurationClientAgent.class.getName() + (1 + 6 * i),
                            ConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new BaseInDto(token),
                                    ArgumentType.USER_GET_PREFERENCES
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateUpdateUserPreferences() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_update_preferences.xml");
        NodeList userPreferencesInDto = document.getElementsByTagName("UserPreferencesInDto");
        for (int i = 0; i < userPreferencesInDto.getLength(); i++) {
            Node item = userPreferencesInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<String>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String token = params.get(0);
            Integer minimalMinutesLeft = Integer.valueOf(params.get(1));
            Integer startSearchingMinutesLeft = Integer.valueOf(params.get(2));
            Boolean acceptAlways = Boolean.valueOf(params.get(3));
            String avoidedStationNames = params.get(4);
            String preferredStationNames = params.get(5);

            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent
                    (ConfigurationClientAgent.class.getName() + (2 + 6 * i),
                            ConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new UserPreferencesInDto(token, minimalMinutesLeft,
                                            startSearchingMinutesLeft, acceptAlways,
                                            avoidedStationNames, preferredStationNames),
                                    ArgumentType.USER_UPDATE_PREFERENCES
                            }
                    );
            agentControllerLogin.start();
        }
    }
}
