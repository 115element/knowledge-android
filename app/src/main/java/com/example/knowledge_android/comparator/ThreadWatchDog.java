/*
 * Created on 2004-2-2
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.example.knowledge_android.comparator;

import java.util.*;
import java.io.*;

/** The ThreadWatchDog class is the watchdog used to monitor threads
 * in an application. There are two dogs used to monitor, the ALPHA_DOG
 * and the BETA_DOG. The BETA_DOG's sole responsibility is to monitor
 * the ALPHA_DOG. The ALPHA_DOG monitors the BETA_DOG and all assigned
 * threads for monitoring. If a thread dies, the dogs will report an
 * exception. If the dead thread is a MutableThread, the dog will try to
 * restart it.
 */
public class ThreadWatchDog extends MutableThread {

	private static ThreadWatchDog mAlphaThread = null; // alphadog instance
	private static ThreadWatchDog mBetaThread = null; // betadog instance
	private static HashMap mThreadHashMap = null; // vector of all threads
	private static int mAlphaPriority = Thread.NORM_PRIORITY;
	private static int mBetaPriority = Thread.NORM_PRIORITY;
	private static long POLL_TIME = 10000; //  10 second check for TEST Change this to 60000 for one min. etc

    private static PrintWriter logger;

    private ThreadWatchDog() {
		super();
		mThreadHashMap = new HashMap();

	}

    /**
     * set logger
     * @param writer
     */
	public static void setLogger(PrintWriter writer) {
        logger = writer;
    }
    
    /** The getInstance method insures that the ALPHA_DOG (and therefore also
	 * the BETA_DOG) is a singleton. It is static so that it can be referenced
	 * throughout the application within the JVM.
	 */
	public synchronized static ThreadWatchDog getInstance() {
		if (mAlphaThread == null) {
			mAlphaThread = new ThreadWatchDog();
			mAlphaThread.setPriority(mAlphaPriority);
			mAlphaThread.setName("ALPHA DOG");
			mBetaThread = new ThreadWatchDog();
			mBetaThread.setPriority(mBetaPriority);
			mBetaThread.setName("BETA DOG");
			// these dogs watch each other
			mAlphaThread.put(mBetaThread);
			mBetaThread.put(mAlphaThread);
		}
		return mAlphaThread;
	}

	/** This is the run method for the thread.
	 */
	public void run() {

		while (true) {
    		checkAllThreads();
    		poll();
		}
	}

	/** The checkAllThreads method for a dog checks each thread which has been
	 * put into the HashMap.
	 */

	public synchronized void checkAllThreads() {

		Set lThreadSet = mThreadHashMap.keySet();
		Iterator lThreadIterator = lThreadSet.iterator();

		while (lThreadIterator.hasNext()) {
			String lThreadKey = (String) lThreadIterator.next();
			Object lTestThread = mThreadHashMap.get(lThreadKey);

			if (lTestThread instanceof MutableThread) {

				if (!((MutableThread) lTestThread).isAlive()) {
					try {
                        log("Thread " + ((MutableThread) lTestThread).getName() + "  is dead");
                        log("Attempting to restart thread : " + lThreadKey + " ...");
						// attempt to restart the thread by clearing and restarting
						((MutableThread) lTestThread).createThread();
//						log("Starting " + lThreadKey + " ...");
						((MutableThread) lTestThread).start();
						log("Restart " + lThreadKey + " Successfully");
					} catch (Exception eException) {

					}
				} else {
//                    log(
//						"Mutable Thread " + ((MutableThread) lTestThread).getName() + "  is Alive");
				}

//                log("Thread " + ((MutableThread) lTestThread).getName() + " is alive ");
			} else { // not a Mutable thread
				if (!((Thread) lTestThread).isAlive()) {
                    log("Thread " + lThreadKey + " is dead");
				} else {
					// thread is alive
//                    log("Thread " + ((Thread) lTestThread).getName() + "  is Alive");
				}
			}
		}

	}

	/** The put method puts a mutable thread into the HashMap for monitoring by a dog.
	 * Note that this is an overloaded method which also allows for non-mutable Threads to
	 * be monitored as well.
	 */
	public synchronized void put(MutableThread aMutableThread) {
		mThreadHashMap.put(aMutableThread.getName(), aMutableThread);
	}

	/** The put method causes the dog to monitor the passed Thread.
	 */
	public synchronized void put(Thread aThread) {
		mThreadHashMap.put(aThread.getName(), aThread);
	}

	private /*synchronized*/ void poll() {
		try {
            //Bruce/20080606/ I think we don't have to wait() here. Change to sleep().
            //this.wait(POLL_TIME);
            Thread.sleep(POLL_TIME);
		} catch (InterruptedException e) {
            e.printStackTrace(logger);
		}
	}
    
    private synchronized static void log(String message) {
        if (logger != null) {
            message = System.currentTimeMillis() + " " + message;
            logger.println(message);
            logger.flush();
        }
    }

}
