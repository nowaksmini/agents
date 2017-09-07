package com.mini.smartroad.simulation;

import com.mini.smartroad.Main;
import com.mini.smartroad.client.configuration.StationConfigurationClientAgent;
import com.mini.smartroad.client.login_register.StationLoginRegisterClientAgent;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.configure.StationNegotiationStrategyInDto;
import com.mini.smartroad.dto.in.configure.StationPreferencesInDto;
import com.mini.smartroad.dto.in.login.StationLoginInDto;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;
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

public class StationSimulation {
    public void start() {
        try {
            simulateRegisterStation();
            Thread.sleep(3000);
            simulateLoginStation();
            Thread.sleep(10000);
            simulateUpdateStationPreferences();
            simulateUpdateStationStrategies();
            Thread.sleep(10000);
            simulateGetStationPreferences();
            simulateGetStationStrategies();
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

    private void simulateLoginStation() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_login.xml");
        NodeList stationLoginInDto = document.getElementsByTagName("StationLoginInDto");
        for (int i = 0; i < stationLoginInDto.getLength(); i++) {
            Node item = stationLoginInDto.item(i);
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
                    (StationLoginRegisterClientAgent.class.getName() + (4 * i),
                            StationLoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new StationLoginInDto(login, password),
                                    ArgumentType.STATION_LOGIN
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateRegisterStation() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_register.xml");
        NodeList stationRegisterInDto = document.getElementsByTagName("StationRegisterInDto");
        for (int i = 0; i < stationRegisterInDto.getLength(); i++) {
            Node item = stationRegisterInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<String>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    if (childNodes.item(j).getNodeName().equals("AddressDto")) {
                        NodeList addressDto = childNodes.item(j).getChildNodes();
                        for (int a = 0; a < addressDto.getLength(); a++) {
                            if (addressDto.item(a).getNodeType() == Node.ELEMENT_NODE) {
                                params.add(addressDto.item(a).getTextContent());
                            }
                        }
                    } else {
                        params.add(childNodes.item(j).getTextContent());
                    }
                }
            }
            String userName = params.get(0);
            String name = params.get(1);
            String email = params.get(2);
            String logo = params.get(3);
            String phone = params.get(4);
            double longitude = Double.valueOf(params.get(5));
            double latitude = Double.valueOf(params.get(6));
            AddressDto addressDto = new AddressDto(
                    params.get(7),
                    params.get(8),
                    params.get(9),
                    params.get(10),
                    params.get(11),
                    params.get(12),
                    params.get(13)
            );
            AgentController agentControllerRegister = Main.getAgentContainer().createNewAgent
                    (StationLoginRegisterClientAgent.class.getName() + (3 + 4 * i),
                            StationLoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new StationRegisterInDto(userName, name, email, logo, phone, longitude, latitude, addressDto),
                                    ArgumentType.STATION_REGISTER
                            }
                    );
            agentControllerRegister.start();
        }
    }

    private void simulateGetStationPreferences() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_get_preferences.xml");
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
                    (StationConfigurationClientAgent.class.getName() + (3 + 6 * i),
                            StationConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new BaseInDto(token),
                                    ArgumentType.STATION_GET_PREFERENCES
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateUpdateStationPreferences() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_update_preferences.xml");
        NodeList baseInDto = document.getElementsByTagName("StationPreferencesInDto");
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
            String name = params.get(1);
            String email = params.get(2);
            String phone = params.get(3);
            String logo = params.get(4);
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent
                    (StationConfigurationClientAgent.class.getName() + (4 + 6 * i),
                            StationConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new StationPreferencesInDto(token, name, email, phone, logo),
                                    ArgumentType.STATION_UPDATE_PREFERENCES
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateGetStationStrategies() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_get_strategies.xml");
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
                    (StationConfigurationClientAgent.class.getName() + (5 + 6 * i),
                            StationConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new BaseInDto(token),
                                    ArgumentType.STATION_GET_NEGOTIATION_STRATEGY
                            }
                    );
            agentControllerLogin.start();
        }
    }

    private void simulateUpdateStationStrategies() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_station_update_strategies.xml");
        NodeList baseInDto = document.getElementsByTagName("StationNegotiationStrategyInDto");
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
            Integer groupSize0 = Integer.valueOf(params.get(1));
            Integer groupSize1 = Integer.valueOf(params.get(2));
            Integer groupSize2 = Integer.valueOf(params.get(3));
            Integer groupSize3 = Integer.valueOf(params.get(4));
            Integer groupSize4 = Integer.valueOf(params.get(5));

            Integer pointsGroupSize0 = Integer.valueOf(params.get(6));
            Integer pointsGroupSize1 = Integer.valueOf(params.get(7));
            Integer pointsGroupSize2 = Integer.valueOf(params.get(8));
            Integer pointsGroupSize3 = Integer.valueOf(params.get(9));
            Integer pointsGroupSize4 = Integer.valueOf(params.get(10));

            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent
                    (StationConfigurationClientAgent.class.getName() + (6 * i),
                            StationConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new StationNegotiationStrategyInDto(
                                            token,
                                            groupSize0,
                                            groupSize1,
                                            groupSize2,
                                            groupSize3,
                                            groupSize4,
                                            pointsGroupSize0,
                                            pointsGroupSize1,
                                            pointsGroupSize2,
                                            pointsGroupSize3,
                                            pointsGroupSize4
                                    ),
                                    ArgumentType.STATION_UPDATE_NEGOTIATION_STRATEGY
                            }
                    );
            agentControllerLogin.start();
        }
    }
}
