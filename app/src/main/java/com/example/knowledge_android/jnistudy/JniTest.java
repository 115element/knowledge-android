package com.example.knowledge_android.jnistudy;

public class JniTest {
    static {
        //Relative path to load dynamic libraries
        //System.loadLibrary("CMakeProject1");

        //The absolute path to load the dynamic library
        //System.load("C:\\Users\\sxh\\source\\repos\\CMakeProject1\\out\\build\\x64-Debug\\CMakeProject1\\libCMakeProject1.so");
    }

    public native void getString();

    public static void main(String[] args) {
        System.out.printf("ss");
        JniTest jniTest = new JniTest();
        jniTest.getString();
    }
}


