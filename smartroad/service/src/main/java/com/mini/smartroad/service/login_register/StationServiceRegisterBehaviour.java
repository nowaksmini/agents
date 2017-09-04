package com.mini.smartroad.service.login_register;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.login_register.LoginRegisterStationOutDto;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.model.StationStrategyEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class StationServiceRegisterBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceRegisterBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(registerStation((StationRegisterInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private LoginRegisterStationOutDto registerStation(StationRegisterInDto stationRegisterInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto;
        Session session = HibernateUtils.getSessionFactory().openSession();
        List list = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("name", stationRegisterInDto.getName()))
                .add(Restrictions.eq("longitude", stationRegisterInDto.getLongitude()))
                .add(Restrictions.eq("latitude", stationRegisterInDto.getLatitude()))
                .list();
        session.close();
        if (list != null && !list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_STATION_ALREADY_DATABASE);
            return new LoginRegisterStationOutDto(statusOutDto);
        }
        session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        StationEntity stationEntity = createStation(stationRegisterInDto);
        session.save(stationEntity);
        transaction.commit();
        session.close();
        statusOutDto = new StatusOutDto();
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return new LoginRegisterStationOutDto(stationEntity.getToken(), stationEntity.getSecretCode(), statusOutDto);
    }

    private StationEntity createStation(StationRegisterInDto stationRegisterInDto) {
        AddressEntity addressEntity = new AddressEntity();
        AddressDto addressDto = stationRegisterInDto.getAddressDto();
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setProvince(addressDto.getDistinct());
        addressEntity.setPostalCode(addressDto.getPostalCode());
        addressEntity.setNumber(addressDto.getNumber());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setExtraNumber(addressDto.getExtraNumber());

        StationEntity stationEntity = new StationEntity();
        stationEntity.setUserName(stationRegisterInDto.getUserName());
        stationEntity.setSecretCode(CryptoUtils.generateStationSecretCode(stationRegisterInDto.getName(), stationRegisterInDto.getLongitude(),
                stationRegisterInDto.getLatitude()));
        stationEntity.setName(stationRegisterInDto.getName());
        stationEntity.setToken(CryptoUtils.generateStationToken(stationRegisterInDto.getName(),
                stationRegisterInDto.getLongitude(), stationRegisterInDto.getLatitude()));

        stationEntity.setEmail(stationRegisterInDto.getEmail());
        stationEntity.setPhone(stationRegisterInDto.getPhone());
        stationEntity.setLatitude(stationRegisterInDto.getLatitude());
        stationEntity.setLongitude(stationRegisterInDto.getLongitude());
        stationEntity.setLogo(stationRegisterInDto.getLogo());

        stationEntity.setAddress(addressEntity);

        StationStrategyEntity stationStrategyEntity = new StationStrategyEntity();
        stationStrategyEntity.setStation(stationEntity);

        stationEntity.setStationStrategy(stationStrategyEntity);

        return stationEntity;
    }

    @Override
    public boolean done() {
        return sent;
    }

}