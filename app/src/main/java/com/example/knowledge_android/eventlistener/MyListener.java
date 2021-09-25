package com.example.knowledge_android.eventlistener;

import java.util.EventListener;

public interface MyListener extends EventListener {

    void transactionChanged(MyEvent var1);
}
