package com.mini.smartroad.client.login_register;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseDoneBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.login_register.LoginRegisterStationOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class StationClientRegisterResponseBehaviour extends BaseDoneBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_STATION));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_REGISTER + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info("-------------------REGISTER STATION START-------------------------");
                    try {
                        LoginRegisterStationOutDto contentObject = (LoginRegisterStationOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " REGISTER success for: \n" + contentObject);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------REGISTER STATION END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    logger.info("-------------------REGISTER STATION START-------------------------");
                    try {
                        StatusOutDto contentObject = ((LoginRegisterStationOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " REGISTER failed, message: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    logger.info("-------------------REGISTER STATION END-------------------------");
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info("-------------------REGISTER STATION START-------------------------");
                    logger.info(getAgent().getName() + " REGISTER failed with error \n" + msg.getContent());
                    logger.info("-------------------REGISTER STATION END-------------------------");
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
