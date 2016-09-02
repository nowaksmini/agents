package com.mini.smartroad.service.search;

import com.mini.smartroad.service.base.BaseAgent;

public class SearchServiceAgent extends BaseAgent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new WaitForSearchBehaviour());
    }
}