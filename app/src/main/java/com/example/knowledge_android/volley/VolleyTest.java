package com.example.knowledge_android.volley;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.knowledge_android.OneApplication;

import java.util.HashMap;
import java.util.Map;

/*
框架：Volley/Xutils(异步网络请求)
a.不需要我们启动分线程，框架内部接收到请求自动会在分线程
b.如何返回给你结果数据？切换到主线程调用监听器的回调方法。
 */


//StringRequest:获取字符串结果的请求
//JsonRequest:获取Json数据结果的请求
//ImageRequest:获取图片结果的请求

public class VolleyTest {

    //1.创建请求队列对象(1次)
    //2.创建请求对象StringRequest
    //3.将请求添加到队列中


    private RequestQueue requestQueue = Volley.newRequestQueue(OneApplication.getInstance().getBaseContext());


    //使用Volley提交get请求
    public void get() {
        String url = "http://www.baidu.com";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //在主线程执行(也就是UI线程)
                //这里可以直接更新UI
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("请求错误");
            }
        });
        //将请求添加到队列中
        requestQueue.add(request); //请求发送是在子线程执行的
    }


    //使用Volley提交post请求
    public void post() {
        String url = "http://www.baidu.com";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //在主线程执行(也就是UI线程)
                //这里可以直接更新UI
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("请求错误");
            }
        }) {

            //重写此方法，返回参数map作为请求体
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("name","alex");
                map.put("age","16");
                return super.getParams();
            }
        };
        //将请求添加到队列中
        requestQueue.add(request); //请求发送是在子线程执行的
    }
}
