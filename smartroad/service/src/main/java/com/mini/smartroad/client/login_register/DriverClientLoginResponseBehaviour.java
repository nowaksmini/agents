package com.mini.smartroad.client.login_register;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.login_register.LoginRegisterUserOutDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverClientLoginResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_LOGIN + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------LOGIN USER START-------------------------");
                    try {
                        LoginRegisterUserOutDto contentObject = (LoginRegisterUserOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " LOGIN success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------LOGIN USER END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------LOGIN USER START-------------------------");
                    try {
                        StatusOutDto contentObject = ((LoginRegisterUserOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " LOGIN failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------LOGIN USER END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------LOGIN USER START-------------------------");
                    logger.info(getAgent().getName() + " LOGIN failed with error \n" + msg.getContent());
                    logger.info("-------------------LOGIN USER END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
