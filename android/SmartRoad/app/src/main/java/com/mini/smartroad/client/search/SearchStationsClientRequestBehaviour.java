package com.mini.smartroad.client.search;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.FindStationsInDto;

import java.io.IOException;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class SearchStationsClientRequestBehaviour extends BaseDoneBehaviour {

    private FindStationsInDto searchStationsInDto;

    public SearchStationsClientRequestBehaviour(String userToken, double latitude, double longitude, Long distance) {
        searchStationsInDto = new FindStationsInDto(userToken, latitude, longitude, distance);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.SEARCH_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(searchStationsInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_SEARCH);
        message.setProtocol(Utils.PROTOCOL_FIND_STATIONS);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new SearchStationsClientResponseBehaviour());
        isDone = true;
    }
}
