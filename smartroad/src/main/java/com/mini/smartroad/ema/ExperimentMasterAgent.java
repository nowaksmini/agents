package com.mini.smartroad.ema;

import com.mini.smartroad.common.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ExperimentMasterAgent extends Agent {

    @Override
    protected void setup() {
        // TODO Auto-generated method stub
        //super.setup();
        AID[] spammers = Utils.spammers;
        AID[] consumers = Utils.consumers;

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        template.addServices(sd);

        AID[] agents = null;

        addBehaviour(new StartBehaviour(this,
                spammers,
                consumers));
    }
}
