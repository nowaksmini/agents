package pl.edu.pw.mini.smartroad;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForStartBehaviour extends SimpleBehaviour {
    boolean isDone = false;
    private Behaviour nextBehaviour;

    public WaitForStartBehaviour(Behaviour b) {
        this.nextBehaviour = b;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null)
        {
            isDone = true;
        }
        else {
            System.out.println(myAgent.getLocalName() + " is waiting for message to begin");
            block();
        }
    }

    @Override
    public boolean done() {
        if (isDone)
        {
            myAgent.addBehaviour(nextBehaviour);
            return true;
        }
        return false;
    }
}