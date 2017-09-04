package com.mini.smartroad.service.configuration;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.configure.StationNegotiationStrategyInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.configure.StationNegotiationStrategyOutDto;
import com.mini.smartroad.model.StationEntity;
import com.mini.smartroad.model.StationStrategyEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class StationServiceUpdateStrategyBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public StationServiceUpdateStrategyBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(updateStationStrategy((StationNegotiationStrategyInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StationNegotiationStrategyOutDto updateStationStrategy(StationNegotiationStrategyInDto stationNegotiationStrategyInDto, ACLMessage aclMessage) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        StationNegotiationStrategyOutDto stationNegotiationStrategyOutDto;
        StatusOutDto statusOutDto;
        List list = session.createCriteria(StationEntity.class)
                .add(Restrictions.eq("token", stationNegotiationStrategyInDto.getToken()))
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

        stationStrategy.setGroupSize0(stationNegotiationStrategyInDto.getGroupSize0());
        stationStrategy.setGroupSize1(stationNegotiationStrategyInDto.getGroupSize1());
        stationStrategy.setGroupSize2(stationNegotiationStrategyInDto.getGroupSize2());
        stationStrategy.setGroupSize3(stationNegotiationStrategyInDto.getGroupSize3());
        stationStrategy.setGroupSize4(stationNegotiationStrategyInDto.getGroupSize4());
        stationStrategy.setPointsGroupSize0(stationNegotiationStrategyInDto.getPointsGroupSize0());
        stationStrategy.setPointsGroupSize1(stationNegotiationStrategyInDto.getPointsGroupSize1());
        stationStrategy.setPointsGroupSize2(stationNegotiationStrategyInDto.getPointsGroupSize2());
        stationStrategy.setPointsGroupSize3(stationNegotiationStrategyInDto.getPointsGroupSize3());
        stationStrategy.setPointsGroupSize4(stationNegotiationStrategyInDto.getPointsGroupSize4());

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(stationStrategy);
        session.getTransaction().commit();
        session.close();

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