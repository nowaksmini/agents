package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.BaseInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientMakeGroupRequestBehaviour extends BaseDoneBehaviour {

    private BaseInDto baseInDto;

    public DriverClientMakeGroupRequestBehaviour(BaseInDto baseInDto) {
        this.baseInDto = baseInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(Utils.HELPER_SERVICE_AGENT, AID.ISLOCALNAME));
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
