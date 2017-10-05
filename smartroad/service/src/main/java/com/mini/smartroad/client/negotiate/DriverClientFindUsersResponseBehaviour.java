package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.negotiate.FindUsersOutDto;
import com.mini.smartroad.simulation.DriverSimulation;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientFindUsersResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchProtocol(Utils.PROTOCOL_FIND_USERS + Utils.SUFFIX_RESPONSE));
        if (msg != null) {
            if (msg.getOntology().equals(Utils.ONTOLOGY_USER)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------FIND USERS START-------------------------");
                    try {
                        FindUsersOutDto contentObject = (FindUsersOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " FIND USERS success for: \n" + contentObject);
                        DriverSimulation.findUsersOutDto = contentObject;
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------FIND USERS END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------FIND USERS START-------------------------");
                    try {
                        StatusOutDto contentObject = ((FindUsersOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " FIND USERS failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------FIND USERS END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------FIND USERS START-------------------------");
                    logger.info(getAgent().getName() + " FIND USERS failed with error \n" + msg.getContent());
                    logger.info("-------------------FIND USERS END-------------------------");
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
