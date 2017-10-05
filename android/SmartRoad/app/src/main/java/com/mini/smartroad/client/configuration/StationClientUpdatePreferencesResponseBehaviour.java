package com.mini.smartroad.client.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.configure.StationPreferencesOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class StationClientUpdatePreferencesResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_STATION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_UPDATE_PREFERENCES + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------UPDATE STATION PREFERENCES START-------------------------");
                    try {
                        StationPreferencesOutDto contentObject = (StationPreferencesOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " UPDATE PREFERENCES success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE STATION PREFERENCES END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------UPDATE STATION PREFERENCES START-------------------------");
                    try {
                        StatusOutDto contentObject = ((StationPreferencesOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " UPDATE PREFERENCES failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE STATION PREFERENCES END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------UPDATE STATION PREFERENCES START-------------------------");
                    logger.info(getAgent().getName() + " UPDATE PREFERENCES failed with error \n" + msg.getContent());
                    logger.info("-------------------UPDATE STATION PREFERENCES END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
