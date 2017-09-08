package com.mini.smartroad.simulation;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Simulation {

    public static void start() {
        new StationSimulation().start();
        new DriverSimulation().start();
//            AgentController agentControllerSearch = Main.getAgentContainer().createNewAgent(DriverTrackClientAgent.class.getName(),
//                    DriverTrackClientAgent.class.getName(), new Object[]{"2b1e1cf6d6adea1dd3d8", 50.01d, 50.01d, new Long(5)});
//            agentControllerSearch.start();
//            AgentController agentControllerAction = Main.getAgentContainer().createNewAgent(DriverActionClientAgent.class.getName(),
//                    DriverActionClientAgent.class.getName(), new Object[]{"2b1e1cf6d6adea1dd3d8", "42b62e5b8f97e22e64b6", ActionType.LIKE, Boolean.FALSE, Boolean.FALSE});
//            agentControllerAction.start();
    }

    static Document readXmlDocument(String name) throws ParserConfigurationException, IOException, SAXException {
        System.out.println(String.format("------- START read file %s -----------", name));

        ClassLoader classLoader = Simulation.class.getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        System.out.println(String.format("------- END read file %s -----------", name));
        return doc;
    }
}
