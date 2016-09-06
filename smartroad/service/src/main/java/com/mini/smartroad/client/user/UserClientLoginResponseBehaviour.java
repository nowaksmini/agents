package com.mini.smartroad.client.user;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.UserOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class UserClientLoginResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LOGIN + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    try {
                        UserOutDto contentObject = (UserOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " login success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    try {
                        StatusOutDto contentObject = ((UserOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " login failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info(getAgent().getName() + " login failed with error \n" + msg.getContent());
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
