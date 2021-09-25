package com.example.knowledge_android.eventlistener;

import java.util.EventObject;

public class MyEvent extends EventObject {

    public String msg;

    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
