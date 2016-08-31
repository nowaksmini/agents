package com.mini.smartroad.service;

import com.mini.smartroad.HibernateUtil;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.StatusDto;
import com.mini.smartroad.dto.StatusType;
import com.mini.smartroad.dto.UserLoginDto;
import com.mini.smartroad.model.UserEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class UserServiceLoginBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public UserServiceLoginBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        super(receiver, ontology, protocol, inputContent);
    }

    @Override
    public void action() {
        super.action();
        ACLMessage message = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        message.setOntology(getOntology());
        message.setProtocol(getProtocol());
        message.addReceiver(getReceiver());
        try {
            message.setContentObject(loginUser((UserLoginDto) getInputContent(), message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusDto loginUser(UserLoginDto userLoginDto, ACLMessage aclMessage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List list = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", userLoginDto.getLogin()))
                .add(Restrictions.eq("password", userLoginDto.getPassword()))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return new StatusDto(StatusType.ERROR, MessageProperties.ERROR_USER_LOGIN);
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            return new StatusDto(StatusType.ERROR, MessageProperties.ERROR_USER_UNIQUE);
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return new StatusDto();
    }

    @Override
    public boolean done() {
        return sent;
    }

}