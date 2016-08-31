package pl.edu.pw.mini.smartroad.sa;

import jade.core.Agent;
import pl.edu.pw.mini.smartroad.WaitForStartBehaviour;

public class SpammerAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new WaitForStartBehaviour(new SpammerBehaviour()));
    }
}