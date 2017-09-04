package com.mini.smartroad.service.configuration;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.BaseInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.configure.StationNegotiationStrategyOutDto;
import com.mini.smartroad.dto.out.configure.StationPreferencesOutDto;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.StationStrategyEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class StationServiceGetStrategyBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceGetStrategyBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(getStationStrategy((BaseInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StationNegotiationStrategyOutDto getStationStrategy(BaseInDto baseInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        StationNegotiationStrategyOutDto stationNegotiationStrategyOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", baseInDto.getToken()))
                .list();
        session.close();
        if (list == null || list.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_NO_STATION_WITH_TOKEN);
            stationNegotiationStrategyOutDto = new StationNegotiationStrategyOutDto(statusOutDto);
            return stationNegotiationStrategyOutDto;
        }
        if (list.size() > 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto = new StatusOutDto(StatusType.ERROR, MessageProperties.ERROR_STATION_UNIQUE);
            stationNegotiationStrategyOutDto = new StationNegotiationStrategyOutDto(statusOutDto);
            return stationNegotiationStrategyOutDto;
        }
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        StationEntity stationEntity = (StationEntity) list.get(0);
        StationStrategyEntity stationStrategy = stationEntity.getStationStrategy();

        stationNegotiationStrategyOutDto = new StationNegotiationStrategyOutDto(
                stationStrategy.getGroupSize0(),
                stationStrategy.getGroupSize1(),
                stationStrategy.getGroupSize2(),
                stationStrategy.getGroupSize3(),
                stationStrategy.getGroupSize4(),
                stationStrategy.getPointsGroupSize0(),
                stationStrategy.getPointsGroupSize1(),
                stationStrategy.getPointsGroupSize2(),
                stationStrategy.getPointsGroupSize3(),
                stationStrategy.getPointsGroupSize4(),
                statusOutDto);
        return stationNegotiationStrategyOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}