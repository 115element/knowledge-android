package com.example.knowledge_android.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.knowledge_android.MainActivity;

public class BootBroadCastReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT.equals(intent.getAction())) {
            ComponentName comp = new ComponentName(context.getPackageName(), MainActivity.class.getName());
            context.startActivity(new Intent().setComponent(comp).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
