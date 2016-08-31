package com.mini.smartroad.ema;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class StartBehaviour extends OneShotBehaviour{
	private AID spammers[], consumers[];
	public StartBehaviour(Agent agent, AID[] spammers, AID[] consumers) {
		myAgent = agent;
		this.spammers = spammers;
		this.consumers = consumers;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);		
		for (AID aid : consumers) {
			message.addReceiver(aid);
		}		
		for (AID aid : spammers) {
			message.addReceiver(aid);
		}
		myAgent.send(message);
		long startTime = System.currentTimeMillis();
		System.out.println(myAgent.getLocalName() + " wyslano start");
		myAgent.addBehaviour(new EndBehaviour(spammers.length, startTime));
	}

}
