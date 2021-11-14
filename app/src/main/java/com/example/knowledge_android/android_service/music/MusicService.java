package com.example.knowledge_android.android_service.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.knowledge_android.R;

/**
 * 播放音乐的Service
 */

public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");

        if ("play".equals(action)) {
            play();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private MediaPlayer mediaPlayer;

    public void play(){
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.beep);
            mediaPlayer.start(); //开启播放

//            boolean playing = mediaPlayer.isPlaying(); //是否播放中
//
//            mediaPlayer.pause(); //暂停播放
//
//
//            mediaPlayer.stop(); //停止
//            mediaPlayer.reset(); //重置
//            mediaPlayer.release(); //释放加载的音乐资源
        }
    }
}
