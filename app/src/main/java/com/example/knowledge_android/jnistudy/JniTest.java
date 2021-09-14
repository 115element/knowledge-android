package com.example.knowledge_android.jnistudy;

public class JniTest {
    static {
        //Relative path to load dynamic libraries
        //System.loadLibrary("hello");

        //The absolute path to load the dynamic library
        System.load("C:\\Users\\sxh\\source\\repos\\CMakeProject1\\out\\build\\x64-Debug\\CMakeProject1\\CMakeProject1.dll");
    }

    public native void getString();

    public static void main(String[] args) {
        JniTest jniTest = new JniTest();
        jniTest.getString();
    }
}


