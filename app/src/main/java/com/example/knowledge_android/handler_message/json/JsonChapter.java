package com.example.knowledge_android.handler_message.json;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 1.将JSON格式的字符串{}转换为java对象，使用原生API
 * 2.将JSON格式的字符串{}转换为java对象，使用GSON
 * 3.将JSON格式的字符串{}转换为java对象的List，使用原生API
 * 4.将JSON格式的字符串{}转换为java对象的List，使用GSON
 *
 * 5.将java对象转换为JSON字符串{},使用GSON
 * 6.将java对象的List转换为JSON字符串[],使用GSON
 */
public class JsonChapter {

    //1.将JSON格式的字符串{}转换为java对象，使用原生API
    public void testJsonToObject() throws JSONException {
        String jsonString = "{\"id\":2,\"name\":\"大侠\",\"price\":12.3}";

        JSONObject jsonObject = new JSONObject(jsonString);
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");

        ABean aBean = new ABean(id,name,price);
    }


    //2.将JSON格式的字符串{}转换为java对象，使用GSON
    public void testJsonObject2(){
        String jsonString = "{\"id\":2,\"name\":\"大侠\",\"price\":12.3}";
        ABean aBean = new Gson().fromJson(jsonString, ABean.class);
    }

    //3.将JSON格式的字符串{}转换为java对象的List，使用原生API
    public void test3() throws JSONException {
        String jsonString = "[{\"id\":2,\"name\":\"大侠\",\"price\":12.3}," +
                "{\"id\":2,\"name\":\"大侠\",\"price\":12.3}]";
        JSONArray jsonArray = new JSONArray(jsonString);

        List<ABean> aBeanList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            double price = jsonObject.getDouble("price");

            ABean aBean = new ABean(id,name,price);
            aBeanList.add(aBean);
        }
    }

    //4.将JSON格式的字符串{}转换为java对象的List，使用GSON
    public void test4(){
        String jsonString = "[{\"id\":2,\"name\":\"大侠\",\"price\":12.3}," +
                "{\"id\":2,\"name\":\"大侠\",\"price\":12.3}]";

        List<ABean> aBeanList = new Gson().fromJson(jsonString, new TypeToken<List<ABean>>() {
        }.getType());
    }


    //5.将java对象转换为JSON字符串{},使用GSON
    public void test5(){
        ABean aBean = new ABean(1, "2", 3);
        String s = new Gson().toJson(aBean);
    }


    //6.将java对象的List转换为JSON字符串[],使用GSON
    public void test6(){
        List<ABean> aBeanList = new ArrayList<>();
        aBeanList.add(new ABean(1, "2", 3));
        aBeanList.add(new ABean(1, "3", 3));

        String s = new Gson().toJson(aBeanList);
    }


    //特殊的JSON字符串，注意key存在空格
    public void jsonToMap(){
        String jsonString = "{\"id\":2,\"my name\":\"大侠\",\"price\":12.3}";

        Map<String,Object> map = new Gson().fromJson(jsonString, new TypeToken<Map<String, Object>>() {
        }.getType());

        Object id = map.get("id");
        Object my_name = map.get("my name");
        Object price = map.get("price");
    }
}
