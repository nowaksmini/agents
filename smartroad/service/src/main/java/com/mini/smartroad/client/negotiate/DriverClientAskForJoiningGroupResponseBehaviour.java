package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.negotiate.AskForJoiningGroupOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientAskForJoiningGroupResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchProtocol(Utils.PROTOCOL_NEGOTIATE + Utils.SUFFIX_RESPONSE));
        if (msg != null) {
            if (msg.getOntology().equals(Utils.ONTOLOGY_USER)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------ASK USER TO JOIN GROUP START-------------------------");
                    try {
                        AskForJoiningGroupOutDto contentObject = (AskForJoiningGroupOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " ASK USER TO JOIN GROUP success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------ASK USER TO JOIN GROUP END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------ASK USER TO JOIN GROUP START-------------------------");
                    try {
                        StatusOutDto contentObject = ((AskForJoiningGroupOutDto) msg.getContentObject()).getStationOutDto();
                        logger.info(getAgent().getName() + " ASK USER TO JOIN GROUP failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------ASK USER TO JOIN GROUP END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------ASK USER TO JOIN GROUP START-------------------------");
                    logger.info(getAgent().getName() + " ASK USER TO JOIN GROUP failed with error \n" + msg.getContent());
                    logger.info("-------------------ASK USER TO JOIN GROUP END-------------------------");
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