package com.mini.smartroad.ema;

import com.mini.smartroad.common.Utils;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class EndBehaviour extends SimpleBehaviour {

    private int n;
    private int results = 0;
    private long startTime;
    private long timeResults[] = new long[Utils.spammers.length];

    public EndBehaviour(int numberOfTests, long startTime) {
        n = numberOfTests;
        this.startTime = startTime;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            for (int i = 0; i < Utils.consumers.length; ++i) {
                if (msg.getSender().getLocalName().equals(Utils.consumers[i].getLocalName())) {
                    //tmp
                    //System.out.println("Roznica: " + (System.currentTimeMillis() - startTime));
                    timeResults[i] = System.currentTimeMillis() - startTime;
                }
            }
            ++results;
        } else
            block();
    }

    @Override
    public boolean done() {
        if (results >= n) {
            System.out.println("Rezultaty:");
            for (int i = 0; i < timeResults.length; ++i) {
                System.out.println(i + " :" + timeResults[i]);
            }
            return true;
        }
        return false;
    }

}
