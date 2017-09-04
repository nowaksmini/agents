package com.mini.smartroad.service.login_register;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.register.StationRegisterInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
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

    private StatusOutDto registerStation(StationRegisterInDto stationRegisterInDto, ACLMessage aclMessage) {
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
            return statusOutDto;
        }
        session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        StationEntity stationEntity = createStation(stationRegisterInDto);
        session.save(stationEntity);
        transaction.commit();
        session.close();
        statusOutDto = new StatusOutDto();
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
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

        // todo check if station entity does not already exist, if so add it
        StationEntity stationEntity = new StationEntity();
        stationEntity.setName(stationRegisterInDto.getName());
        stationEntity.setToken(CryptoUtils.generateStationToken(stationRegisterInDto.getName(),
                stationRegisterInDto.getLongitude(), stationRegisterInDto.getLatitude()));

        stationEntity.setEmail(stationRegisterInDto.getEmail());
        stationEntity.setPhone(stationRegisterInDto.getPhone());
        stationEntity.setLatitude(stationRegisterInDto.getLatitude());
        stationEntity.setLongitude(stationRegisterInDto.getLongitude());
        stationEntity.setLogo(stationRegisterInDto.getLogo());

        stationEntity.setToken(CryptoUtils.generateStationDetailsToken(stationRegisterInDto.getName(),
                stationRegisterInDto.getLongitude(), stationEntity.getLatitude()));
        stationEntity.setAddress(addressEntity);

        return stationEntity;
    }

    @Override
    public boolean done() {
        return sent;
    }

}