package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.BaseInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientBecomeRepresentativeRequestBehaviour extends BaseDoneBehaviour {

    private BaseInDto baseInDto;
    private String gasStationAgentName;

    public DriverClientBecomeRepresentativeRequestBehaviour(BaseInDto baseInDto, String gasStationAgentName) {
        this.baseInDto = baseInDto;
        this.gasStationAgentName = gasStationAgentName;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(gasStationAgentName, AID.ISLOCALNAME));
        try {
            message.setContentObject(baseInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_BECOME_REPRESENTATIVE);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverClientBecomeRepresentativeResponseBehaviour());
        isDone = true;
    }
}
