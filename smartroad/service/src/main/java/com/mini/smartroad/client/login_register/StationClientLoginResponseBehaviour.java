package com.mini.smartroad.client.login_register;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.login_register.LoginRegisterStationOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class StationClientLoginResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_STATION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LOGIN + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------LOGIN STATION START-------------------------");
                    try {
                        LoginRegisterStationOutDto contentObject = (LoginRegisterStationOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " LOGIN success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------LOGIN STATION END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------LOGIN STATION START-------------------------");
                    try {
                        StatusOutDto contentObject = ((LoginRegisterStationOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " LOGIN failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------LOGIN STATION END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------LOGIN STATION START-------------------------");
                    logger.info(getAgent().getName() + " LOGIN failed with error \n" + msg.getContent());
                    logger.info("-------------------LOGIN STATION END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
