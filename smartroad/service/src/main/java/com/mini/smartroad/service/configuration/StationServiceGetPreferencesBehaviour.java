package com.mini.smartroad.service.configuration;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.configure.StationPreferencesOutDto;
import com.mini.smartroad.dto.out.configure.UserPreferencesOutDto;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.UserEntity;
import com.mini.smartroad.model.UserPreferencesEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sun.swing.StringUIClientPropertyKey;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class StationServiceGetPreferencesBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceGetPreferencesBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(getStationPreferences((BaseInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StationPreferencesOutDto getStationPreferences(BaseInDto baseInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        StationPreferencesOutDto stationPreferencesOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", baseInDto.getToken()))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_NO_STATION_WITH_TOKEN);
            stationPreferencesOutDto = new StationPreferencesOutDto(statusOutDto);
            return stationPreferencesOutDto;
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_STATION_UNIQUE);
            stationPreferencesOutDto = new StationPreferencesOutDto(statusOutDto);
            return stationPreferencesOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        StationEntity stationEntity = (StationEntity) list.get(0);
        stationPreferencesOutDto = new StationPreferencesOutDto(
                stationEntity.getName(),
                stationEntity.getEmail(),
                stationEntity.getPhone(),
                stationEntity.getLogo(),
                generateStringAddress(stationEntity.getAddress()),
                statusOutDto);
        return stationPreferencesOutDto;
    }

    public static String generateStringAddress(AddressEntity addressEntity) {
        return addressEntity.getStreet() + " Street " +
                addressEntity.getNumber() + ", " + addressEntity.getPostalCode() + " " + addressEntity.getCity();
    }

    @Override
    public boolean done() {
        return sent;
    }

}