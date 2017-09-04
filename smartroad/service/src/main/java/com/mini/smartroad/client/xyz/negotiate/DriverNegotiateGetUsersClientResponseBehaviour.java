package com.mini.smartroad.client.xyz.negotiate;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.FindStationsOutDto;
import com.mini.smartroad.dto.out.FindUsersOutDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DriverNegotiateGetUsersClientResponseBehaviour extends BaseStopAgentBehaviour {

//    @Override
//    public void action() {
//        super.action();
//        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_NEGOTIATE));
//        if (msg != null) {
//            if (msg.getProtocol().equals(Utils.PROTOCOL_FIND_USERS + Utils.SUFFIX_RESPONSE)) {
//                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
//                    logger.info(getAgent().getName() + " found users success.");
//                    try {
//                        FindUsersOutDto contentObject = (FindUsersOutDto) msg.getContentObject();
//                        logger.info(getAgent().getName() + " found users: \n" + contentObject);
//                        // send information about negotiation to all users
//
//                    } catch (UnreadableException e) {
//                        e.printStackTrace();
//                    }
//                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
//                    try {
//                        StatusOutDto contentObject = ((FindStationsOutDto) msg.getContentObject()).getStatusOut();
//                        logger.info(getAgent().getName() + " find users failed, message: \n" + contentObject.getMessage());
//                    } catch (UnreadableException e) {
//                        e.printStackTrace();
//                    }
//                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
//                    logger.info(getAgent().getName() + " find users failed with error \n" + msg.getContent());
//                }
//                isDone = true;
//            }
//        } else {
//            block();
//        }
//    }
//
//    private void sentInformationToUsers(FindUsersOutDto findUsersOutDto){
//
//    }
}
