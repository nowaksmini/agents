package com.mini.smartroad.client;

import com.mini.smartroad.Main;
import com.mini.smartroad.client.action.ActionClientAgent;
import com.mini.smartroad.client.search.SearchStationsClientAgent;
import com.mini.smartroad.client.station.StationClientAgent;
import com.mini.smartroad.client.user.UserClientAgent;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.StationRegisterInDto;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.Random;

public class StartSimulation {
    private static final String[] streets = {"Marszałkowska", "Koszykowa", "Żelaza", "Nowakowskiego"};
    private static final double[][] coordinates = {{52.233357, 21.00925}, {52.223105, 21.00204}, {
            52.237825, 20.989509}, {52.220356, 21.011578}};
    private static final String postalCode = "02-237";
    private static final String email = "test_stacja@gmail";
    private static final String phone = "123 456 789";
    private static final String[] logos = {"https://upload.wikimedia.org/wikipedia/commons/1/1e/Logo_statoil.png",
            "http://www.bp.com/content/dam/bp/images/general/graphics/1-1/bp-logo.jpg",
            "http://vignette2.wikia.nocookie.net/logopedia/images/2/2d/Lukoil-logo-620x236.png/revision/latest?cb=20160304213348",
            "https://upload.wikimedia.org/wikipedia/en/thumb/e/e8/Shell_logo.svg/829px-Shell_logo.svg.png"};
    private static final String country = "Poland";
    private static final String distinct = "Mazowieckie";
    private static final String city = "Warszawa";
    private static final String[] names = {"Statoil", "Bp", "Lukoil", "Shell"};
    private static final String[] fullNames = {"Statoil\n" +
            "Stacja paliw", "BP stacja paliw Polska", "Bp sp zoo", "Grupa Shell Polska"};

    public static void start() {
        try {
            AgentController agentControllerRegister = Main.getAgentContainer().createNewAgent(UserClientAgent.class.getName() + 1,
                    UserClientAgent.class.getName(), new String[]{"test@gmail.com", "test_firstName", "test_lastName", "password"});
            agentControllerRegister.start();
            AgentController agentControllerLogin = Main.getAgentContainer().createNewAgent(UserClientAgent.class.getName() + 2,
                    UserClientAgent.class.getName(), new String[]{"test@gmail.com", "password"});
            agentControllerLogin.start();
            AgentController agentControllerStations = Main.getAgentContainer().createNewAgent(StationClientAgent.class.getName(),
                    StationClientAgent.class.getName(), buildStation());
            agentControllerStations.start();
            AgentController agentControllerSearch = Main.getAgentContainer().createNewAgent(SearchStationsClientAgent.class.getName(),
                    SearchStationsClientAgent.class.getName(), new Object[]{"2b1e1cf6d6adea1dd3d8", 50.01d, 50.01d, new Long(5)});
            agentControllerSearch.start();
            AgentController agentControllerAction = Main.getAgentContainer().createNewAgent(ActionClientAgent.class.getName(),
                    ActionClientAgent.class.getName(), new Object[]{"2b1e1cf6d6adea1dd3d8", "42b62e5b8f97e22e64b6", ActionType.LIKE, Boolean.FALSE, Boolean.FALSE});
            agentControllerAction.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    private static StationRegisterInDto[] buildStation() {
        StationRegisterInDto[] stations = new StationRegisterInDto[4];
        for (int i = 0; i < stations.length; i++) {
            AddressDto addressDto = new AddressDto(country, distinct, city, streets[i],
                    new Random().nextInt() % 20 + "", null, postalCode);
            stations[i] = new StationRegisterInDto(names[i], fullNames[i], email, logos[i], phone, coordinates[i][0], coordinates[i][1], addressDto);
        }
        return stations;
    }
}
