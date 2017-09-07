package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.negotiate.InviteToGroupInDto;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class DriverClientInviteToGroupRequestBehaviour extends BaseDoneBehaviour {

    private InviteToGroupInDto inviteToGroupInDto;

    public DriverClientInviteToGroupRequestBehaviour(InviteToGroupInDto inviteToGroupInDto) {
        this.inviteToGroupInDto = inviteToGroupInDto;
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(inviteToGroupInDto.getInvitedUserAgentName(), AID.ISLOCALNAME));
        try {
            message.setContentObject(inviteToGroupInDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setOntology(Utils.ONTOLOGY_USER);
        message.setProtocol(Utils.PROTOCOL_INVITE_TO_GROUP);
        ((BaseAgent) myAgent).sendMessage(message);
        myAgent.addBehaviour(new DriverClientMakeGroupResponseBehaviour());
        isDone = true;
    }
}
