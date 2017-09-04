package com.mini.smartroad.simulation;

import com.mini.smartroad.Main;
import com.mini.smartroad.client.login_register.LoginRegisterClientAgent;
import com.mini.smartroad.common.ArgumentsType;
import com.mini.smartroad.dto.AddressDto;
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
                    (LoginRegisterClientAgent.class.getName() + (4 * i),
                            LoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new StationLoginInDto(login, password),
                                    ArgumentsType.STATION_LOGIN
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
                    (LoginRegisterClientAgent.class.getName() + (3 + 4 * i),
                            LoginRegisterClientAgent.class.getName(),
                            new Object[]{
                                    new StationRegisterInDto(userName, name, email, logo, phone, longitude, latitude, addressDto),
                                    ArgumentsType.STATION_REGISTER
                            }
                    );
            agentControllerRegister.start();
        }
    }

}
