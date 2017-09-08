package com.mini.smartroad.client.action;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientInformServiceInvitationAcceptedResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_USER_ACCEPT_INVITATION + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE START-------------------------");
                    try {
                        StatusOutDto contentObject = (StatusOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " ACCEPTED INVITATION SEND TO SERVICE success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE START-------------------------");
                    try {
                        StatusOutDto contentObject = (StatusOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " ACCEPTED INVITATION SEND TO SERVICE failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE START-------------------------");
                    logger.info(getAgent().getName() + " ACCEPTED INVITATION SEND TO SERVICE failed with error \n" + msg.getContent());
                    logger.info("-------------------ACCEPTED INVITATION SEND TO SERVICE END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }
}
