package com.example.knowledge_android.android_content_provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//模拟APP-B 调用者

public class PersonResolverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void insert() {
        //1.得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.knowledge_android.android_content_provider.personprovider/person/");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "jack");
        Uri insert = contentResolver.insert(uri, contentValues);
    }

    public void update() {
        //1.得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.knowledge_android.android_content_provider.personprovider/person/1");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "jack");
        int updateCount = contentResolver.update(uri, contentValues, null, null);
    }

    public void delete() {
        //1.得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.knowledge_android.android_content_provider.personprovider/person/1");
        int deleteCount = contentResolver.delete(uri, null, null);
    }

    //通过ContentResolver调用ContentProvider查询指定记录
    public void query() {
        //1.得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        //2.调用其query，得到cursor
        Uri uri = Uri.parse("content://com.example.knowledge_android.android_content_provider.personprovider/person/1");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //取出cursor中的数据，并显示
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
        }
    }

    //通过ContentResolver调用ContentProvider查询所有记录
    public void queryAll() {
        //1.得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        //2.调用其query，得到cursor
        Uri uri = Uri.parse("content://com.example.knowledge_android.android_content_provider.personprovider/person/");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //取出cursor中的数据，并显示
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
        }
        cursor.close();
    }
}
