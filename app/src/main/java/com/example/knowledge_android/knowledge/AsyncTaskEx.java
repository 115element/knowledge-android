package com.example.knowledge_android.knowledge;

import android.os.AsyncTask;

//异步任务
public class AsyncTaskEx<T> extends AsyncTask<Object, Object, T> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected T doInBackground(Object... objects) {
        return null;
    }


    /**
     * After task execution method
     */
    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
    }
}
