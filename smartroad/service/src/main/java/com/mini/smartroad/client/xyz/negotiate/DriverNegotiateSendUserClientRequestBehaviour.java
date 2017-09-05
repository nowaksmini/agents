package com.mini.smartroad.client.xyz.negotiate;

import com.mini.smartroad.base.BaseDoneBehaviour;


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
