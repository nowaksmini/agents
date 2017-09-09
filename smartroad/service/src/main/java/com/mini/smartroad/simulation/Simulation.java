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
