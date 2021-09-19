package com.example.knowledge_android.statemachine;

import java.util.EventObject;

public abstract class State {

    public abstract void entry(EventObject event, State sourceState);

    public abstract Class<? extends State> exit(EventObject event, State sinkState);
}
