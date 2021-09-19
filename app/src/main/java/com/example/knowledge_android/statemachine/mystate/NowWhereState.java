package com.example.knowledge_android.statemachine.mystate;

import com.example.knowledge_android.statemachine.State;

import java.util.EventObject;

public class NowWhereState extends State {

    //这个State退出后，去哪个State
    public Class<? extends State> exitState;
    //来源State
    public State sourceState;

    @Override
    public void entry(EventObject event, State sourceState) {

    }

    @Override
    public Class<? extends State> exit(EventObject event, State sinkState) {
        return null;
    }
}
