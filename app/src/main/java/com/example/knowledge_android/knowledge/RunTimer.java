package com.example.knowledge_android.knowledge;

import android.util.Log;

import java.util.Date;
import java.util.Stack;

public class RunTimer {

    Stack<Long> clocks = new Stack<>();
    Stack<Long> lapClocks = new Stack<>();

    void start(String tag, String message) {
        long now = new Date().getTime();
        clocks.push(now);
        lapClocks.push(now);
        Log.i("Start", "[PROFILE] " + tag + " " + message);
    }

    void stop(String message) {
        Long since = clocks.pop();
        lapClocks.pop();
        Log.i("Stop", "[PROFILE] " + message + " elapsed: " + (new Date().getTime() - since) + "}ms");
    }

    void lap(String message) {
        Long last = lapClocks.pop();
        long now = new Date().getTime();
        lapClocks.push(now);
        Log.i("LAP", "[PROFILE] " + message + " elapsed: " + (now - last) + "ms");
    }
}
