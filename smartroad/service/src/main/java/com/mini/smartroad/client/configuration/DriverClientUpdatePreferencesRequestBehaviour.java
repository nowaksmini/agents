package com.mini.smartroad.client.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.configure.UserPreferencesInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientUpdatePreferencesRequestBehaviour extends BaseDoneBehaviour {

    private UserPreferencesInDto userPreferencesInDto;

    public DriverClientUpdatePreferencesRequestBehaviour(UserPreferencesInDto userPreferencesInDto) {
        this.userPreferencesInDto = userPreferencesInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.CONFIGURE_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(userPreferencesInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_UPDATE_PREFERENCES);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new DriverClientUpdatePreferencesResponseBehaviour());
    }
}
