package com.mini.smartroad.service.configuration;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.configure.UserPreferencesOutDto;
import com.mini.smartroad.dto.out.login_register.LoginRegisterUserOutDto;
import com.mini.smartroad.model.UserEntity;
import com.mini.smartroad.model.UserPreferencesEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DriverServiceGetPreferencesBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceGetPreferencesBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(getUserPreferences((BaseInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private UserPreferencesOutDto getUserPreferences(BaseInDto baseInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        UserPreferencesOutDto userPreferencesOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("token", baseInDto.getToken()))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_NO_USER_WITH_TOKEN);
            userPreferencesOutDto = new UserPreferencesOutDto(statusOutDto);
            return userPreferencesOutDto;
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_USER_UNIQUE);
            userPreferencesOutDto = new UserPreferencesOutDto(statusOutDto);
            return userPreferencesOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        UserEntity userEntity = (UserEntity) list.get(0);
        UserPreferencesEntity preferences = userEntity.getPreferences();
        userPreferencesOutDto = new UserPreferencesOutDto(
                preferences.getMinimalMinutesLeft(),
                preferences.getStartSearchingMinutesLeft(),
                preferences.getAcceptAlways(),
                preferences.getAvoidedStationNames(),
                preferences.getPreferredStationNames(),
                statusOutDto);
        return userPreferencesOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}