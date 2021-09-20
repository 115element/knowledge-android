package com.example.knowledge_android.knowledge;

import android.util.Log;

import java.time.LocalDate;
import java.util.Date;
import java.util.Stack;


/*
用法：
RunTimer timer = new RunTimer();
timer.start("TAG", "OneApplication Start >>>");
//TODO...1
timer.lap("打印做事情1用了多久");
//TODO...2
timer.lap("打印做事情2用了多久");
 */

public class RunTimer {

    private final Stack<Long> clocks = new Stack<>();   //存储RunTimer开始的时间
    private final Stack<Long> lapClocks = new Stack<>();//存储上一次lap的时间

    //启动RunTimer
    public void start(String tag, String message) {
        long now = new Date().getTime();
        clocks.push(now);
        lapClocks.push(now);
        Log.i("Start", "[PROFILE] " + tag + " " + message);
    }

    //打印从RunTimer的Start开始到结束，总共用了多少时间
    public void stop(String message) {
        Long since = clocks.pop();
        lapClocks.pop();
        Log.i("Stop", "[PROFILE] " + message + " elapsed: " + (new Date().getTime() - since) + "}ms");
    }

    //打印距离Start开始的时间
    public void lap(String message) {
        Long last = lapClocks.pop();
        long now = new Date().getTime();
        lapClocks.push(now);
        Log.i("LAP", "[PROFILE] " + message + " elapsed: " + (now - last) + "ms");
    }
}
