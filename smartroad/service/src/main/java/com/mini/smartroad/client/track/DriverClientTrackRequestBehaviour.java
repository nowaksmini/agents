package com.mini.smartroad.client.track;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.track.UserTrackInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientTrackRequestBehaviour extends BaseDoneBehaviour {

    private UserTrackInDto userTrackInDto;

    public DriverClientTrackRequestBehaviour(UserTrackInDto userTrackInDto) {
        this.userTrackInDto = userTrackInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.TRACKER_SERVICE_AGENT, AID.ISLOCALNAME));
        try {
            message.setContentObject(userTrackInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_TRACK);
        ((BaseAgent) myAgent).sendMessage(message);
        isDone = true;
        myAgent.addBehaviour(new DriverClientTrackResponseBehaviour());
    }
}
