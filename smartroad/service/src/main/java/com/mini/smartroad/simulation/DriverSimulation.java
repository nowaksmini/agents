package com.mini.smartroad.simulation;

import com.mini.smartroad.DriverRuntimeInfo;
import com.mini.smartroad.Main;
import com.mini.smartroad.client.configuration.DriverConfigurationClientAgent;
import com.mini.smartroad.client.login_register.DriverLoginRegisterClientAgent;
import com.mini.smartroad.client.negotiate.DriverNegotiateClientAgent;
import com.mini.smartroad.client.track.DriverTrackClientAgent;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.ArgumentType;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.configure.UserPreferencesInDto;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.in.negotiate.FindStationsInDto;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;
import com.mini.smartroad.dto.in.track.UserTrackInDto;
import com.mini.smartroad.dto.out.configure.UserPreferencesOutDto;
import com.mini.smartroad.dto.out.negotiate.StationOutDto;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.h2.util.StringUtils;
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
            Thread.sleep(3000);
            simulateDriverTrip();
        } catch (StaleProxyException | SAXException | ParserConfigurationException |
                IOException | InterruptedException e) {
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
                    (DriverLoginRegisterClientAgent.class.getName() + (1 + 4 * i),
                            DriverLoginRegisterClientAgent.class.getName(),
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
                    (DriverLoginRegisterClientAgent.class.getName() + (2 + 4 * i),
                            DriverLoginRegisterClientAgent.class.getName(),
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
            AgentController agentController = Main.getAgentContainer().createNewAgent
                    (DriverConfigurationClientAgent.class.getName() + (1 + 6 * i),
                            DriverConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new BaseInDto(token),
                                    ArgumentType.USER_GET_PREFERENCES
                            }
                    );
            agentController.start();
        }
    }

    private void simulateUpdateUserPreferences() throws StaleProxyException, IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_update_preferences.xml");
        NodeList userPreferencesInDto = document.getElementsByTagName("UserPreferencesInDto");
        for (int i = 0; i < userPreferencesInDto.getLength(); i++) {
            Node item = userPreferencesInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<>();
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

            AgentController agentController = Main.getAgentContainer().createNewAgent
                    (DriverConfigurationClientAgent.class.getName() + (2 + 6 * i),
                            DriverConfigurationClientAgent.class.getName(),
                            new Object[]{
                                    new UserPreferencesInDto(token, minimalMinutesLeft,
                                            startSearchingMinutesLeft, acceptAlways,
                                            avoidedStationNames, preferredStationNames),
                                    ArgumentType.USER_UPDATE_PREFERENCES
                            }
                    );
            agentController.start();
        }
    }

    private void simulateDriverTrip() throws IOException, SAXException, ParserConfigurationException, StaleProxyException {
        List<DriverRuntimeInfo> driverRuntimeInfos = new LinkedList<>();
        List<Double> directions = new LinkedList<>();
        Document document = Simulation.readXmlDocument("simulation_driver_start_trip_positions.xml");
        NodeList userPreferencesInDto = document.getElementsByTagName("UserTrackInDto");
        for (int i = 0; i < userPreferencesInDto.getLength(); i++) {
            Node item = userPreferencesInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String token = params.get(0);
            Double longitude = Double.valueOf(params.get(1));
            Double latitude = Double.valueOf(params.get(2));
            Boolean wantsToNegotiate = Boolean.valueOf(params.get(3));
            Double direction = Double.valueOf(params.get(4));

            driverRuntimeInfos.add(new DriverRuntimeInfo(token, longitude, latitude, wantsToNegotiate));
            directions.add(direction);
        }

        double longDiff = 0.01;
        double latDiff = 0.03;
        int i = 0;

        while (i < 2) {
            for (int j = 0; j < driverRuntimeInfos.size(); j++) {
                Double direction = directions.get(j);
                DriverRuntimeInfo driverRuntimeInfo = driverRuntimeInfos.get(j);
                driverRuntimeInfo.setLongitude(driverRuntimeInfo.getLongitude() + direction * longDiff);
                driverRuntimeInfo.setLatitude(driverRuntimeInfo.getLatitude() + direction * latDiff);
                AgentController agentController = Main.getAgentContainer().createNewAgent
                        (DriverTrackClientAgent.class.getName() + ((driverRuntimeInfos.size()) * i + j),
                                DriverTrackClientAgent.class.getName(),
                                new Object[]{
                                        new UserTrackInDto(driverRuntimeInfo.getToken(),
                                                driverRuntimeInfo.getLatitude(),
                                                driverRuntimeInfo.getLongitude(),
                                                driverRuntimeInfo.isWantsToNegotiate()),
                                        ArgumentType.USER_POSITION_TRACK
                                }
                        );
                try {
                    agentController.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }
            }
            i++;
            try {
                Thread.sleep(2000);
                simulateGetStations(i);
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateGetStations(int iteration) throws StaleProxyException, IOException, SAXException, ParserConfigurationException, InterruptedException {
        Document document = Simulation.readXmlDocument("simulation_driver_get_stations.xml");
        NodeList baseInDto = document.getElementsByTagName("BaseInDto");
        for (int i = 0; i < baseInDto.getLength(); i++) {
            Node item = baseInDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    params.add(childNodes.item(j).getTextContent());
                }
            }
            String token = params.get(0);
            AgentController agentController = Main.getAgentContainer().createNewAgent
                    (DriverNegotiateClientAgent.class.getName() + (i + 6 * iteration),
                            DriverNegotiateClientAgent.class.getName(),
                            new Object[]{
                                    new FindStationsInDto(token, 20L),
                                    ArgumentType.USER_FIND_STATIONS
                            }
                    );
            agentController.start();
        }
        Thread.sleep(3000);
        simulateBecomeRepresentative();
    }

    private void simulateBecomeRepresentative() throws IOException, SAXException, ParserConfigurationException {
        Document document = Simulation.readXmlDocument("simulation_driver_get_stations_no_groups_response.xml");
        NodeList stationOutDto = document.getElementsByTagName("StationOutDto");
        List<StationOutDto> stationOutDtos = new LinkedList<>();
        for (int i = 0; i < stationOutDto.getLength(); i++) {
            Node item = stationOutDto.item(i);
            NodeList childNodes = item.getChildNodes();
            List<String> params = new LinkedList<>();
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
            String name = params.get(0);
            String email = params.get(1);
            String logo = params.get(2);
            String token = params.get(3);
            String phone = params.get(4);
            double longitude = Double.valueOf(params.get(5));
            double latitude = Double.valueOf(params.get(6));
            int counter = Integer.valueOf(params.get(7));
            int points = Integer.valueOf(params.get(8));
            int minAmountCars = Integer.valueOf(params.get(9));
            ActionType actionType = StringUtils.isNullOrEmpty(params.get(10)) ? null
                    : ActionType.valueOf(params.get(10));
            AddressDto addressDto = new AddressDto(
                    params.get(11),
                    params.get(12),
                    params.get(13),
                    params.get(14),
                    params.get(15),
                    params.get(16),
                    params.get(17)
            );
            StationOutDto stationOutDto1 = new StationOutDto(name, email, logo, token, phone, longitude, latitude,
                    counter, points, minAmountCars, actionType, addressDto);
            stationOutDtos.add(stationOutDto1);
        }
        selectBestStation(null, null);

        // TODO process received data obout ststsions
        // select best one - algorytm
        // wyślij prośbę do danej stacji benzynowej o zostanie reprezentantem - agent done
        // dla stacji benzynowej wyślij accepp - agent done
        // po otrzymaniu accept formuj grupę - nie można formować grupy która już jest - agent done
        // pytaj CU o agentów userów skłonnych do negocjacji - agent done
        // wyślij info do userów o negocjacjach
        // dostaj akceptację od user-a 1
        // dla stacji benzynowaej która nie spełnia wymagań wyśłij zapytanie o podwyżkę
        // stacja zraca nową kwotę
        // stacja informuje CU o zmianie kwoty
        // stacja potwierdza ze przyjechali ludzie
        // rozdanie punktów
    }

    private StationOutDto selectBestStation(List<StationOutDto> stationOutDtos, UserPreferencesOutDto userPreferencesOutDto){
        return null;
    }

}
