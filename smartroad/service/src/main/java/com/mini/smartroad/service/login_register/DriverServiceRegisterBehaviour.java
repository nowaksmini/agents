package com.mini.smartroad.service.login_register;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.register.UserRegisterInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.login_register.LoginRegisterUserOutDto;
import com.mini.smartroad.model.UserEntity;
import com.mini.smartroad.model.UserPreferencesEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DriverServiceRegisterBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceRegisterBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(registerUser((UserRegisterInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private LoginRegisterUserOutDto registerUser(UserRegisterInDto userRegisterInDto, ACLMessage aclMessage) {
        LoginRegisterUserOutDto loginRegisterUserOutDto;
        StatusOutDto statusOutDto;
        Session session = HibernateUtils.getSessionFactory().openSession();
        List list = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", userRegisterInDto.getEmail()))
                .list();
        session.close();
        if (list != null && !list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_USER_ALREADY_DATABASE);
            loginRegisterUserOutDto = new LoginRegisterUserOutDto(statusOutDto);
            return loginRegisterUserOutDto;
        }
        session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = createUser(userRegisterInDto);
        session.save(userEntity);
        transaction.commit();
        statusOutDto = new StatusOutDto();
        loginRegisterUserOutDto = new LoginRegisterUserOutDto(userEntity.getToken(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), statusOutDto);
        session.close();
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return loginRegisterUserOutDto;
    }

    private UserEntity createUser(UserRegisterInDto userRegisterInDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLastName(userRegisterInDto.getLastName());
        userEntity.setFirstName(userRegisterInDto.getFirstName());
        userEntity.setEmail(userRegisterInDto.getEmail());
        userEntity.setPassword(CryptoUtils.encodePassword(userRegisterInDto.getPassword()));
        userEntity.setToken(CryptoUtils.generateUserToken(userEntity.getEmail()));

        UserPreferencesEntity userPreferencesEntity = new UserPreferencesEntity();
        userPreferencesEntity.setUser(userEntity);

        userEntity.setPreferences(userPreferencesEntity);
        return userEntity;
    }

    @Override
    public boolean done() {
        return sent;
    }

}