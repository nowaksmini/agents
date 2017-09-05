package com.mini.smartroad.client.xyz.negotiate;

import com.mini.smartroad.base.BaseStopAgentBehaviour;

public class DriverNegotiateReceiveProposalResponseBehaviour extends BaseStopAgentBehaviour {

//    @Override
//    public void action() {
//        super.action();
//        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_TRACK));
//        if (msg != null) {
//            if (msg.getProtocol().equals(Utils.PROTOCOL_FIND_STATIONS + Utils.SUFFIX_RESPONSE)) {
//                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
//                    logger.info(getAgent().getName() + " found stations success.");
//                    try {
//                        FindStationsOutDto contentObject = (FindStationsOutDto) msg.getContentObject();
//                        logger.info(getAgent().getName() + " found stations: \n" + contentObject);
//                    } catch (UnreadableException e) {
//                        e.printStackTrace();
//                    }
//                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
//                    try {
//                        StatusOutDto contentObject = ((FindStationsOutDto) msg.getContentObject()).getStatusOut();
//                        logger.info(getAgent().getName() + " find stations failed, message: \n" + contentObject.getMessage());
//                    } catch (UnreadableException e) {
//                        e.printStackTrace();
//                    }
//                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
//                    logger.info(getAgent().getName() + " find stations failed with error \n" + msg.getContent());
//                }
//                isDone = true;
//            }
//        } else {
//            block();
//        }
//    }
}
