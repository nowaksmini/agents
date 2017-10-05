package com.mini.smartroad.client.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.configure.StationNegotiationStrategyOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class StationClientUpdateStrategyResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_STATION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_UPDATE_NEGOTIATION_STRATEGY + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------UPDATE STATION STRATEGY START-------------------------");
                    try {
                        StationNegotiationStrategyOutDto contentObject = (StationNegotiationStrategyOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " UPDATE STRATEGY success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE STATION STRATEGY END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------UPDATE STATION STRATEGY START-------------------------");
                    try {
                        StatusOutDto contentObject = ((StationNegotiationStrategyOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " UPDATE STRATEGY failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE STATION STRATEGY END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------UPDATE STATION STRATEGY START-------------------------");
                    logger.info(getAgent().getName() + " UPDATE STRATEGY failed with error \n" + msg.getContent());
                    logger.info("-------------------UPDATE STATION STRATEGY END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
