package com.example.knowledge_android.pos.setdatetime;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DateTimeLib {
	static {
        try {
            Log.i("JNI", "Trying to load PosDateTimeDrv.so");
            System.loadLibrary("PosDateTimeDrv");
            Log.i("JNI", "load PosDateTimeDrv.so success");
        } catch (UnsatisfiedLinkError ule) {
            Log.e("JNI", "WARNING: Could not load PosDateTimeDrv.so");
        }
	}
    public static native int SetDateTime(int mYear,int mMonth,int mDay,int mHour,int mMinute,int mSecond);
    public static int ClearAppCache(Context mContext) {
        try {
            Intent intent=new Intent();
            intent.setAction("pos.intent.action.CLEAR_APP_CACHE");
            mContext.sendBroadcast(intent);
        } catch(Exception e) {
            return (-1);
        }
        return 0;
    }
}
