package com.example.knowledge_android.statemachine;

import android.util.Log;

import com.example.knowledge_android.statemachine.mystate.InitialState;
import com.example.knowledge_android.statemachine.mystate.NowWhereState;

import java.util.EventObject;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alex
 * @since 2021-09-19 20:17:00
 */
public class StateMachine {

    private static final String TAG = "StateMachine";

    private static StateMachine stateMachine;

    public static StateMachine getInstance() {
        return stateMachine;
    }

    private BlockingQueue<EventObject> eventQueue;

    private Class<? extends State> currentStateClass;

    private final boolean traceState = true; //是否打印日志
    private boolean started;                 //状态机启动标志
    private long timestamp;                  //执行时间记录

    public synchronized void start() {
        stateMachine = this;
        if (started) {
            return;
        }

        init();

        this.started = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                eventQueue = new LinkedBlockingQueue<EventObject>();
                for (; ; ) {
                    try {
                        /**
                         * take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到,BlockingQueue有新的数据被加入;
                         */
                        consumeEvent(eventQueue.take());
                    } catch (Exception e) {
                        Log.i("AAA", "DAEMON", e);
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    /**
     * This method is try to initially walk from InitialState to IdleState.
     */
    private void init() {
        this.currentStateClass = InitialState.class;
        State currentStateInstance = getStateInstance(currentStateClass);

        // Enter InitialState --
        beforeEntry(currentStateClass);
        currentStateInstance.entry(null, null);     // entry InitialState
        afterEntry();

        if (!isTriggerless(currentStateClass)) {
            Log.i(TAG, "状态图中没有定义-[初始化终止返回]");
            return;
        }

        // Exit InitialState --
        beforeExit(currentStateClass);
        Class nextStateClass = currentStateInstance.exit(null, null); // exit InitialState if triggerless
        afterExit();

        // Enter what InitialState was directed --
        beforeEntry(nextStateClass);
        getStateInstance(nextStateClass).entry(null, currentStateInstance);    // entry sink state
        afterEntry();

        this.currentStateClass = nextStateClass;
    }

    private boolean isTriggerless(Class<? extends State> stateClass) {
        List<Tx> transitions = StateFlow.stateChartMap.get(stateClass);
        //如果状态图中没有定义当前State,返回TRUE
        if (transitions == null) {
            return true;
        }
        // If contains a empty event source class entry, then it is triggerless.
        /*any
        只要存在一个满足条件（的element就返回true，否则返回false*/
        for (Tx transition : transitions) {
            if (transition.eventSources == null) {
                return true;
            }
        }
        return false;
        //transitions.any { Tx tx -> tx.eventSources == null }
    }

    private void beforeEntry(Class<? extends State> stateClass) {
        if (traceState) {
            Log.e(TAG, "call " + stateClass.getSimpleName() + ".entry()");
            timestamp = System.currentTimeMillis();
        }
    }

    private void afterEntry() {
        if (traceState) {
            long elapsed = System.currentTimeMillis() - timestamp;
            Log.e(TAG, "entry() is returned ( " + elapsed + "ms)");
        }
    }

    private void beforeExit(Class<? extends State> stateClass) {
        if (traceState) {
            Log.e(TAG, "exit " + stateClass.getSimpleName() + ".exit()");
            timestamp = System.currentTimeMillis();
        }
    }

    private void afterExit() {
        if (traceState) {
            long elapsed = System.currentTimeMillis() - timestamp;
            Log.e(TAG, "entry() is returned ( " + elapsed + "ms)");
        }
    }


    //TODO  获取Class的实例化对象(重要知识点)
    private State getStateInstance(Class<? extends State> clazz) {
        try {
            State state = clazz.newInstance();
            return state;
        } catch (Exception e) {
            Log.i(TAG, "反射获取State实例异常", e);
        }
        return null;
    }

    /**
     * Do the real job for handling a event.
     */
    private void consumeEvent(EventObject event) {
        while (true) {
            Class eventSourceClass;
            if (event == null) {
                eventSourceClass = null;
            } else {
                eventSourceClass = event.getSource().getClass();
            }

            Class sinkStateByStatechart = getSinkState(currentStateClass, eventSourceClass);
            // sinkStateByStatechart has three cases:
            // 1. NoWhereState if this event will not make any state change.
            // 2. null if sink state is determined by source state's exit().
            // 3. otherwise return the sink state class name defined by statechart.

            if (sinkStateByStatechart == NowWhereState.class) {
                return;     // if cannot make state change
            }

            //从状态图中获取的下一个State，如果状态图没有配置，那么就是空。
            final boolean determineSinkStateByExit = sinkStateByStatechart == null;
            // Determine the parameter "sinkStateObjectByStatechart" passing to currentStateClass.exit()
            final State sinkStateObjectByStatechart = sinkStateByStatechart != null ?
                    getStateInstance(sinkStateByStatechart) : null;

            // exit() the current state --
            final State currentStateObject = getStateInstance(currentStateClass);


            beforeExit(currentStateClass);
            Class sinkStateClassReturnFromExit = currentStateObject.exit(event, sinkStateObjectByStatechart);
            afterExit();

            // Determine the sink state --
            // if statechart doc does not specifies the sink state, use the return value of exit()

            final State sinkStateObjectByExit = determineSinkStateByExit ? getStateInstance(sinkStateClassReturnFromExit) : null;

            if (sinkStateObjectByStatechart == null && sinkStateObjectByExit == null) {
                Log.i(TAG, "Err> Don't know where to go");
                return;
            }

            //如果状态图中没有配置，那么就以State返回的为主，如果状态图中配置了下一个State，那么以状态图为主。
            final State sinkStateObject = determineSinkStateByExit ?
                    sinkStateObjectByExit : sinkStateObjectByStatechart;

            // entry() the sink state --
            currentStateClass = sinkStateObject.getClass();

            beforeEntry(sinkStateObject.getClass());
            sinkStateObject.entry(event, currentStateObject);
            afterEntry();

            event = null;

            if (!isTriggerless(currentStateClass)) {
                break;
            }
        }
    }

    /**
     * Determine the sink state class from a source state and an event source class.
     *
     * @return Three cases:
     * <P>1. NoWhereState if this event will not make any state change.
     * <P>2. null if sink state is determined by source state's exit().
     * <P>3. otherwise return the sink state class name defined by statechart.
     */
    private Class getSinkState(Class sourceStateClass, Class eventSourceClass) {
        // assertion
        if (sourceStateClass == null && eventSourceClass == null) {
            return NowWhereState.class;
        }
        List<Tx> transitions = StateFlow.stateChartMap.get(sourceStateClass);
        if (transitions != null) {
            for (Tx tx : transitions) {
                if (tx.eventSources == null || tx.eventSources.contains(eventSourceClass)) {
                    return tx.sinkStateClass;
                }
            }
        }
        return NowWhereState.class;
    }


    public void processEvent(EventObject event) {
        try {
            if (eventQueue == null) {
                return;
            }
            eventQueue.put(event);
        } catch (InterruptedException e) {
            Log.e(TAG, "存入队列事件异常", e);
        }
    }

    void clearEventQueue() {
        if (eventQueue != null) {
            eventQueue.clear();
        }
    }

}
