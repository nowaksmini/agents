package pl.edu.pw.mini.smartroad.mca;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import pl.edu.pw.mini.smartroad.Utils;

public class ConsumingBehaviour extends SimpleBehaviour {
    private int received = 0;
    private String str = "";

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            str += msg.getContent();
            received++;
        } else
            block();
    }

    @Override
    public boolean done() {
        if (received >= Utils.numberOfMessages) {
            myAgent.addBehaviour(new DoneBehaviour());
            return true;
        }
        return false;
    }

}
