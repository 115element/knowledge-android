
// Copyright (c) 2000 hyi
package com.example.knowledge_android.comparator.jni;

/**
 * A Class class.
 * <P>
 * @author dai
 */
public class LineDisplayLocal {

    public native static void ini();
    public native static int show(byte b[], int i);
    //public native static int send(byte b[]);

    static {
        System.loadLibrary("linedisplay");
    }
}

 
