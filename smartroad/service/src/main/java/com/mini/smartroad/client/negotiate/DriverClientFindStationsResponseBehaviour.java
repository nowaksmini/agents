package com.mini.smartroad.client.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.negotiate.FindStationsOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientFindStationsResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_FIND_STATIONS + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------FIND STATIONS START-------------------------");
                    try {
                        FindStationsOutDto contentObject = (FindStationsOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " FIND STATIONS success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------FIND STATIONS END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------FIND STATIONS START-------------------------");
                    try {
                        StatusOutDto contentObject = ((FindStationsOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " FIND STATIONS failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------FIND STATIONS END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------FIND STATIONS START-------------------------");
                    logger.info(getAgent().getName() + " FIND STATIONS failed with error \n" + msg.getContent());
                    logger.info("-------------------FIND STATIONS END-------------------------");
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
