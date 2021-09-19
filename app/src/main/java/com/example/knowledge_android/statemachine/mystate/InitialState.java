package com.example.knowledge_android.statemachine.mystate;

import android.util.Log;

import com.example.knowledge_android.statemachine.State;

import java.util.EventObject;

public class InitialState extends State {

    //这个State退出后，去哪个State
    public Class<? extends State> exitState;
    //来源State
    public State sourceState;

    @Override
    public void entry(EventObject event, State sourceState) {
            if (sourceState == null) {
                Log.i("T","首次加载");
            }
            exitState = IdleState.class;
    }

    @Override
    public Class<? extends State> exit(EventObject event, State sinkState) {
        Log.i("T","退出");
        return exitState;
    }
}
