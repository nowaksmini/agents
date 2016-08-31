package pl.edu.pw.mini.smartroad.mca;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class DoneBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID("ema", AID.ISLOCALNAME));
		message.setContent(myAgent.getLocalName() + " done");
		myAgent.send(message);				
	}
	
}
