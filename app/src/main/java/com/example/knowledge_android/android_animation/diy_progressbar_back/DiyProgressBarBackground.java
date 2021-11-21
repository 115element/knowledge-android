package com.example.knowledge_android.android_animation.diy_progressbar_back;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.j256.ormlite.stmt.query.In;

public class DiyProgressBarBackground extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diy_progress_bar_back);

        progressBar= findViewById(R.id.progressBar2);
        progressBar.setMax(1000);
        start();
    }

    @SuppressLint("StaticFieldLeak")
    public void start(){
        //AsyncTask<Params, Progress, Result>
        new AsyncTask<Void, Integer,Void>(){

            //1.在主线程执行,该方法用于更新UI
            @Override
            protected void onPreExecute() {
                // do ...
                progressBar.setProgress(1);
            }

            //2.在分线程执行，不能更新UI
            @Override
            protected Void doInBackground(Void... voids) {

                for (int i = 0; i < 10; i++) {
                    SystemClock.sleep(1000);
                    //TODO 用于更新进度，也就是会调用下方的，onProgressUpdate方法。
                    publishProgress(1);
                }


                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                progressBar.setProgress(progressBar.getProgress() + 100);
                super.onProgressUpdate(values);
            }

            //3.在主线程执行,该方法用于更新UI
            @Override
            protected void onPostExecute(Void unused) {
                // do ...
                progressBar.setVisibility(View.GONE);
            }

        }.execute();
    }
}
