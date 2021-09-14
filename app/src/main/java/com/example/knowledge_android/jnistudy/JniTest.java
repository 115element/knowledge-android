package com.example.knowledge_android.jnistudy;

public class JniTest {
    static {
        System.loadLibrary("hello");  //载入本地库[这个是我们开发之后生成的库文件名]
    }

    public static native void getString();
}


