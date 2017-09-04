package com.mini.smartroad.client.xyz.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.FindStationsInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


public class DriverNegotiateSendUserClientRequestBehaviour extends BaseDoneBehaviour {

//    private FindStationsInDto searchStationsInDto;
//
//    public DriverNegotiateSendUserClientRequestBehaviour(String userToken, double latitude, double longitude, Long distance) {
//        searchStationsInDto = new FindStationsInDto(userToken, latitude, longitude, distance);
//    }
//
//    @Override
//    public void action() {
//        super.action();
//        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
//        message.addReceiver(new AID(Utils.HELPER_SERVICE_AGENT, AID.ISLOCALNAME));
//        try {
//            message.setContentObject(searchStationsInDto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        message.setOntology(Utils.ONTOLOGY_TRACK);
//        message.setProtocol(Utils.PROTOCOL_FIND_STATIONS);
//        ((BaseAgent) myAgent).sendMessage(message);
//        myAgent.addBehaviour(new DriverNegotiateGasStationClientResponseBehaviour());
//        isDone = true;
//    }
}
