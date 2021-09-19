package com.example.knowledge_android.statemachine;

import java.util.List;

public class Tx {

    List<Class> eventSources;
    Class<? extends State> sinkStateClass;

    /** Triggerless to anywhere decided by source state. */
    public Tx() {}

    /** Triggerless to a sink state. */
    public Tx(Class<? extends State> sinkStateClass) {
        this.sinkStateClass = sinkStateClass;
    }

    /** Trigger by certain event to some sink state decided by source state. */
    public Tx(List<Class> eventSources) {
        this.eventSources = eventSources;
    }

    /** Trigger by certain event to a sink state. */
    public Tx(List<Class> eventSources, Class<? extends State> sinkStateClass) {
        this.eventSources = eventSources;
        this.sinkStateClass = sinkStateClass;
    }
}
