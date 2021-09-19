package com.example.knowledge_android.statemachine.mystate;

import android.util.Log;

import com.example.knowledge_android.statemachine.State;
import com.example.knowledge_android.statemachine.mybutton.ClearButton;
import com.example.knowledge_android.statemachine.mybutton.EnterButton;

import java.util.EventObject;

public class IdleState extends State {

    private static final String TAG = "IdleState";
    //这个State退出后，去哪个State
    public Class<? extends State> exitState;
    //来源State
    public State sourceState;

    @Override
    public void entry(EventObject event, State sourceState) {
        Log.i(TAG,"进入");
    }

    @Override
    public Class<? extends State> exit(EventObject event, State sinkState) {
        Log.i(TAG,"出去");
        Object source = event.getSource();
        if (source instanceof ClearButton) {
            return IdleState.class;
        } else if (source instanceof EnterButton) {
            return IdleState.class;
        }

        return IdleState.class;
    }
}
