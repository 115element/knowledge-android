package com.example.knowledge_android.handler_message.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //泛型Void，代表无返回值。
    public void downloadApk(){
        new AsyncTask<Void,Void,Void>() {

            //1.在主线程执行,该方法用于更新UI
            @Override
            protected void onPreExecute() {
                // do ...
            }

            //2.在分线程执行，不能更新UI
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            //3.在主线程执行,该方法用于更新UI
            @Override
            protected void onPostExecute(Void unused) {
                // do ...
            }
        }.execute();
    }
}
