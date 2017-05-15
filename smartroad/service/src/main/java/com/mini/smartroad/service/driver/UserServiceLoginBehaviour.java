package com.mini.smartroad.service.driver;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.in.UserLoginInDto;
import com.mini.smartroad.dto.out.UserOutDto;
import com.mini.smartroad.model.UserEntity;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
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
        ACLMessage message = new ACLMessage(ACLMessage.FAILURE);
        message.setOntology(getOntology());
        message.setProtocol(getProtocol());
        message.addReceiver(getReceiver());
        try {
            message.setContentObject(loginUser((UserLoginInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private UserOutDto loginUser(UserLoginInDto userLoginInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        UserOutDto userOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", userLoginInDto.getLogin()))
                .add(Restrictions.eq("password", CryptoUtils.encodePassword(userLoginInDto.getPassword())))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_USER_LOGIN);
            userOutDto = new UserOutDto(statusOutDto);
            return userOutDto;
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_USER_UNIQUE);
            userOutDto = new UserOutDto(statusOutDto);
            return userOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        UserEntity userEntity = (UserEntity) list.get(0);
        userOutDto = new UserOutDto(userEntity.getToken(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), statusOutDto);
        return userOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}