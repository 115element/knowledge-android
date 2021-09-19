
// Copyright (c) 2000 hyi
package com.example.knowledge_android.comparator.jni;

/**
 * A Class class.
 * <P>
 * @author dai
 */
public class CashDrawerLocal {

    public native static char opencashdrawer();
    public native static int getdrawerstate();

    static {
        System.loadLibrary("cashdrawer");
    }
}

 
