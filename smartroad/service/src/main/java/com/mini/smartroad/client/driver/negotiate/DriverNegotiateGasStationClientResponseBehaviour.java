package com.mini.smartroad.client.driver.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.FindStationsOutDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverNegotiateGasStationClientResponseBehaviour extends BaseStopAgentBehaviour {

    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_NEGOTIATE));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_FIND_STATIONS + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    logger.info(getAgent().getName() + " gas station accepted driver as representative.");
                    // todo send info to rest users
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    try {
                        StatusOutDto contentObject = ((FindStationsOutDto) msg.getContentObject()).getStatusOut();
                        logger.info(getAgent().getName() + " gas station rejected driver's proposal of becoming representative: \n" + contentObject.getMessage());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info(getAgent().getName() + " start negotiation as representative failed with error \n" + msg.getContent());
                }
                isDone = true;
            }
        } else {
            block();
        }
    }
}
