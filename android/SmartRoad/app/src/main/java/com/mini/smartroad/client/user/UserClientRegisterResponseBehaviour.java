package com.mini.smartroad.client.user;

import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseStopAgentBehaviour;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.UserOutDto;
import com.mini.smartroad.events.FailureEvent;
import com.mini.smartroad.events.UserEvent;

import de.greenrobot.event.EventBus;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class UserClientRegisterResponseBehaviour extends BaseStopAgentBehaviour {
    @Override
    public void action() {
        super.action();
        ACLMessage msg = ((BaseAgent) myAgent).receiveMessage(MessageTemplate.MatchOntology(Utils.ONTOLOGY_USER));
        if (msg != null) {
            if (msg.getProtocol().equals(Utils.PROTOCOL_REGISTER + Utils.SUFFIX_RESPONSE)) {
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    try {
                        UserOutDto contentObject = (UserOutDto) msg.getContentObject();
                        logger.info(getAgent().getName() + " register success for: \n" + contentObject);
                        EventBus.getDefault().post(new UserEvent(contentObject.getToken()));
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new FailureEvent(e.getMessage()));
                    }
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    try {
                        StatusOutDto contentObject = ((UserOutDto) msg.getContentObject()).getStatusOutDto();
                        logger.info(getAgent().getName() + " register failed, message: \n" + contentObject.getMessage());
                        EventBus.getDefault().post(new FailureEvent(contentObject.getMessage()));
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new FailureEvent(e.getMessage()));
                    }
                } else if (msg.getPerformative() == ACLMessage.FAILURE) {
                    logger.info(getAgent().getName() + " register failed with error \n" + msg.getContent());
                    EventBus.getDefault().post(new FailureEvent(msg.getContent()));
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
