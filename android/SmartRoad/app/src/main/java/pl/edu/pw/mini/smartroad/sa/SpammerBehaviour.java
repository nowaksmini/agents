package pl.edu.pw.mini.smartroad.sa;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import pl.edu.pw.mini.smartroad.Utils;

public class SpammerBehaviour extends SimpleBehaviour {

    private int sent = 0;

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("mca" + Utils.ID, AID.ISLOCALNAME));
        message.setContent(Utils.message);
        myAgent.send(message);
        sent++;
    }

    @Override
    public boolean done() {
        return Utils.numberOfMessages <= sent;
    }

}
