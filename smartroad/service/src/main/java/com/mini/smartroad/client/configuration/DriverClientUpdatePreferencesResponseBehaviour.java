package com.mini.smartroad.client.configuration;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.configure.UserPreferencesOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientUpdatePreferencesResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_UPDATE_PREFERENCES + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------UPDATE USER PREFERENCES START-------------------------");
                    try {
                        UserPreferencesOutDto contentObject = (UserPreferencesOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " UPDATE PREFERENCES success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE USER PREFERENCES END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------UPDATE USER PREFERENCES START-------------------------");
                    try {
                        StatusOutDto contentObject = ((UserPreferencesOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " UPDATE PREFERENCES failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------UPDATE USER PREFERENCES END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------UPDATE USER PREFERENCES START-------------------------");
                    logger.info(getAgent().getName() + " UPDATE PREFERENCES failed with error \n" + msg.getContent());
                    logger.info("-------------------UPDATE USER PREFERENCES END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
