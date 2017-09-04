package com.mini.smartroad.service.track;

import com.mini.smartroad.DriverRuntimeInfo;
import com.mini.smartroad.HibernateUtils;
import com.mini.smartroad.Main;
import com.mini.smartroad.base.BaseAgent;
import com.mini.smartroad.base.BaseInteractBehaviour;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.MessageProperties;
import com.mini.smartroad.dto.in.login.UserLoginInDto;
import com.mini.smartroad.dto.in.track.UserTrackInDto;
import com.mini.smartroad.dto.out.StatusOutDto;
import com.mini.smartroad.dto.out.StatusType;
import com.mini.smartroad.dto.out.login_register.LoginRegisterUserOutDto;
import com.mini.smartroad.model.UserEntity;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DriverServiceTrackBehaviour extends BaseInteractBehaviour {

    private boolean sent;

    public DriverServiceTrackBehaviour(AID receiver, String ontology, String protocol, Serializable inputContent) {
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
            message.setContentObject(trackUser((UserTrackInDto) getInputContent(), message));
        } catch (IOException e) {
            message.setContent(e.getMessage());
            message.setPerformative(ACLMessage.FAILURE);
            e.printStackTrace();
        }
        ((BaseAgent) myAgent).sendMessage(message);
        sent = true;
    }

    private StatusOutDto trackUser(UserTrackInDto userTrackInDto, ACLMessage aclMessage) {
        StatusOutDto statusOutDto;
        aclMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        statusOutDto = new StatusOutDto();
        if (Main.drivers.containsKey(userTrackInDto.getToken())) {
            DriverRuntimeInfo driverRuntimeInfo = Main.drivers.get(userTrackInDto.getToken());
            driverRuntimeInfo.setLatitude(userTrackInDto.getLatitude());
            driverRuntimeInfo.setLongitude(userTrackInDto.getLongitude());
            driverRuntimeInfo.setWantsToNegotiate(userTrackInDto.isWantsToNegotiate());
            Main.drivers.put(userTrackInDto.getToken(), driverRuntimeInfo);
        } else {
            Main.drivers.put(userTrackInDto.getToken(), new DriverRuntimeInfo(
                    userTrackInDto.getToken(),
                    userTrackInDto.getLongitude(),
                    userTrackInDto.getLatitude(),
                    userTrackInDto.isWantsToNegotiate()
            ));
        }
        return statusOutDto;
    }

    @Override
    public boolean done() {
        return sent;
    }

}