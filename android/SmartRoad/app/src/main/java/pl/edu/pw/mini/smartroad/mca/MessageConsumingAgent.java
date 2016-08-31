package pl.edu.pw.mini.smartroad.mca;

import jade.core.Agent;
import pl.edu.pw.mini.smartroad.WaitForStartBehaviour;

public class MessageConsumingAgent extends Agent {
		
	@Override
	protected void setup() {
		addBehaviour(new WaitForStartBehaviour(new ConsumingBehaviour()));
	}
}
