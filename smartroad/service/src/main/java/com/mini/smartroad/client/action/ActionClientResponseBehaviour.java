package com.mini.smartroad.client.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ActionClientResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_ACTION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LIKE + Utils.SUFFIX_RESPONSE)
                    || msg.getProtocol().equals(Utils.PROTOCOL_RELIKE + Utils.SUFFIX_RESPONSE)
                    || msg.getProtocol().equals(Utils.PROTOCOL_CONFIRM + Utils.SUFFIX_RESPONSE)
                    || msg.getProtocol().equals(Utils.PROTOCOL_UNLIKE + Utils.SUFFIX_RESPONSE)
                    || msg.getProtocol().equals(Utils.PROTOCOL_UNCONFIRM + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info(getAgent().getName() + " do action succeeded.");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    try {
                        StatusOutDto contentObject = (StatusOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " do action failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info(getAgent().getName() + " do action failed with error \n" + msg.getContent());
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
