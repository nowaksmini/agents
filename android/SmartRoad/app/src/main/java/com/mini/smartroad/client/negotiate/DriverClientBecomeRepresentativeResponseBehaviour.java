package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientBecomeRepresentativeResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchProtocol(Utils.PROTOCOL_BECOME_REPRESENTATIVE + Utils.SUFFIX_RESPONSE));
        if (msg != null) {
            if (msg.getOntology().equals(Utils.ONTOLOGY_USER)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------BECOME_REPRESENTATIVE START-------------------------");
                    try {
                        StatusOutDto contentObject = (StatusOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " BECOME_REPRESENTATIVE success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------BECOME_REPRESENTATIVE END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------BECOME_REPRESENTATIVE START-------------------------");
                    try {
                        StatusOutDto contentObject = (StatusOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " BECOME_REPRESENTATIVE failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------BECOME_REPRESENTATIVE END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------BECOME_REPRESENTATIVE START-------------------------");
                    logger.info(getAgent().getName() + " BECOME_REPRESENTATIVE failed with error \n" + msg.getContent());
                    logger.info("-------------------BECOME_REPRESENTATIVE END-------------------------");
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