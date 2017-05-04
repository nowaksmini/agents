package com.mini.smartroad.service.action;

import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.in.ActionInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.model.ActionEntity;
import com.mini.smartroad.model.StationDetailsEntity;
import com.mini.smartroad.model.UserEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActionServiceBaseBehaviour extends BaseInteractBehaviour {

    protected boolean sent;
    protected UserEntity userEntity;
    protected StationDetailsEntity stationDetailsEntity;

    public ActionServiceBaseBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
        super(receiver, ontology, protocol, inputContent);
    }

    protected StatusOutDto validateActionIn(ActionInDto actionInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = new StatusOutDto();
        Session session = HibernateUtils.getSessionFactory().openSession();
        List users = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("token", actionInDto.getUserToken())).list();
        if (users == null || users.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_USER_WITH_TOKEN + actionInDto.getUserToken());
            session.close();
            return statusOutDto;
        }
        if (users.size() != 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_USER_UNIQUE);
            session.close();
            return statusOutDto;
        }
        userEntity = (UserEntity) users.get(0);
        List stations = session.createCriteria(StationDetailsEntity.class)
                .add(Restrictions.eq("token", actionInDto.getStationToken())).list();
        if (stations == null || stations.isEmpty()) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_NO_STATION_WITH_TOKEN + actionInDto.getStationToken());
            session.close();
            return statusOutDto;
        }
        if (stations.size() != 1) {
            aclMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
            statusOutDto.setStatusType(StatusType.ERROR);
            statusOutDto.setMessage(MessageProperties.ERROR_STATION_UNIQUE);
            session.close();
            return statusOutDto;
        }
        stationDetailsEntity = (StationDetailsEntity) stations.get(0);
        session.close();
        return statusOutDto;
    }

    protected StatusOutDto findPreviousAction(ActionInDto actionInDto, ActionType actionType,
                                              Boolean oldValue, Boolean newValue,
                                              Date end, ACLMessage aclMessage, boolean setNewDate) {
        StatusOutDto statusOutDto = validateActionIn(actionInDto, aclMessage);
        if (statusOutDto.getStatusType() == StatusType.ERROR) {
            return statusOutDto;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        List actions = session.createCriteria(ActionEntity.class)
                .add(Restrictions.eq("actionType", actionType))
                .add(Restrictions.eq("value", oldValue))
                .add(Restrictions.eq("user", userEntity))
                .add(Restrictions.eq("station", stationDetailsEntity))
                .add(Restrictions.le("dateFrom", end))
                .add(Restrictions.ge("dateTo", new Date())).list();
        session.beginTransaction();
        if (actions != null) {
            for (Object action : actions) {
                ((ActionEntity) action).setValue(newValue);
                if (setNewDate) {
                    ((ActionEntity) action).setDateTo(end);
                }
                ((ActionEntity) action).setModifyDate(new Date());
                session.update(action);
            }
        }
        session.getTransaction().commit();
        session.close();
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
    }

    protected StatusOutDto createAction(ActionInDto actionInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto = validateActionIn(actionInDto, aclMessage);
        if (statusOutDto.getStatusType() == StatusType.ERROR) {
            return statusOutDto;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        ActionEntity actionEntity = createActionEntity(actionInDto, userEntity, stationDetailsEntity);
        session.beginTransaction();
        session.save(actionEntity);
        session.getTransaction().commit();
        session.close();
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return statusOutDto;
    }

    private ActionEntity createActionEntity(ActionInDto actionInDto, UserEntity userEntity, StationDetailsEntity stationDetailsEntity) {
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setToken(CryptoUtils.generateActionToken(actionInDto.getActionType(), userEntity.getToken(), stationDetailsEntity.getToken()));
        actionEntity.setValue(actionInDto.getValue());
        actionEntity.setStation(stationDetailsEntity);
        actionEntity.setActionType(actionInDto.getActionType());
        actionEntity.setUser(userEntity);
        actionEntity.setDateFrom(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, actionInDto.getActionType() == ActionType.LIKE ? Utils.LIKE_TIME_DURATION : Utils.COUPON_TIME_DURATION);
        actionEntity.setDateTo(calendar.getTime());
        return actionEntity;
    }

    @Override
    public boolean done() {
        return sent;
    }

}