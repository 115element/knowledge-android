package com.example.knowledge_android.comparator;

import java.util.Map;

public class TimeoutThread implements Runnable {

    private Work work;

    volatile private boolean isTimeout = false;

    private boolean hasDone = false;

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setWorkThread(Thread workThread) {
        this.workThread = workThread;
    }

    public Thread getWorkThread() {
        return workThread;
    }

    public Thread getThread() {
        return thread;
    }

    private Thread thread;
    private Thread workThread;

    public TimeoutThread(Work work, long timeout) {
        this.work = work;
        final long out = timeout;
        thread = new Thread() {
            public void run() {
                try {
//                    CreamToolkit.logMessage("ishelf timeout begin");
                    Thread.sleep(out);
                    isTimeout = true;
//                    CreamToolkit.logMessage("ishelf timeout end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        workThread = new Thread(this);
        workThread.start();
    }

    @Override
    public void run() {
        work.run();
        hasDone = true;
    }

    public Map result() throws Exception {
        if (isTimeout) {
            throw new RuntimeException();
        }
        while (!hasDone) {
            if (isTimeout) {
//                System.out.println("inner");
                throw new RuntimeException("operate timeout!");
            }
            Thread.sleep(10);
        }



        if (work.result() == null) {
            throw new RuntimeException("get paras failed!");
        }
        return work.result();
    }

    public static void main(String[] args) {
        TimeoutThread tt = new TimeoutThread(new Work() {
            String info = null;
            Map m = null;
            public void run() {
                while (true) {
                    System.out.println("execute...");
                    try {
                        Thread.sleep(5000);
                        info = "Hello World";
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public Map result() {
                return m;
            }
        }, 4000);

        try {
            tt.result();
            System.out.println(tt.result());
//            CreamToolkit.logMessage(tt.result());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //CreamToolkit.logMessage(e.getMessage());
        }
    }
}
