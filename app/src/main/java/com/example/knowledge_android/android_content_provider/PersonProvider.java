package com.example.knowledge_android.android_content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 模拟APP-A提供者
 */
public class PersonProvider extends ContentProvider {

    //用来存放所有合法的Uri的容器
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    //保存一些合法的Uri
    // content://com.example.knowledge_android.android_content_provider.personprovider/person   表示不根据id操作
    // content://com.example.knowledge_android.android_content_provider.personprovider/person/3 表示根据id操作
    static {
        //这里的code代表匹配后，返回的结果
        matcher.addURI("com.example.knowledge_android.android_content_provider.personprovider", "/person", 1);
        matcher.addURI("com.example.knowledge_android.android_content_provider.personprovider", "/person/#", 2); //#匹配任意数字
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return false;
    }


    /*
    content://com.example.knowledge_android.android_content_provider.personprovider/person   表示不根据id查询
    content://com.example.knowledge_android.android_content_provider.personprovider/person/3 表示根据id查询
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //得到连接对象
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        //1.匹配uri,返回code
        int code = matcher.match(uri);
        //如果合法，进行查询
        if (code == 1) { //不根据id查询 (这个1对应，上边static代码块中声明的1)
            Cursor person = database.query("person", projection, selection, selectionArgs, null, null, null);
            return person;
        } else if (code == 2) { //根据id查询 (这个2对应，上边static代码块中声明的2)
            //得到id
            long id = ContentUris.parseId(uri);
            Cursor person = database.query("person", projection, "_id=?", new String[]{id + ""}, null, null, null);
            return person;
        } else {
            throw new RuntimeException("查询的uri不合法");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    // content://com.example.knowledge_android.android_content_provider.personprovider/person   插入
    // content://com.example.knowledge_android.android_content_provider.personprovider/person/3 根据id插入(没有)
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        //得到连接对象
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        //1.匹配uri,返回code
        int code = matcher.match(uri);
        //如果合法，进行查询
        if (code == 1) { //插入 (这个1对应，上边static代码块中声明的1)
            long id = database.insert("person", null, contentValues);
            uri = ContentUris.withAppendedId(uri, id);
            database.close();
            return uri;
        } else {
            database.close();
            throw new RuntimeException("插入的uri不合法");
        }
    }


    // content://com.example.knowledge_android.android_content_provider.personprovider/person   不根据id删除
    // content://com.example.knowledge_android.android_content_provider.personprovider/person/3 根据id删除
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        //得到连接对象
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        //1.匹配uri,返回code
        int code = matcher.match(uri);
        //如果合法，进行删除
        if (code == 1) { //不根据id删除 (这个1对应，上边static代码块中声明的1)
            int deleteCount = database.delete("person", s, strings);
            database.close();
            return deleteCount;
        } else if (code == 2) { //根据id删除 (这个2对应，上边static代码块中声明的2)
            //得到id
            long id = ContentUris.parseId(uri);
            int deleteCount = database.delete("person", "_id=" + id, null);
            database.close();
            return deleteCount;
        } else {
            throw new RuntimeException("删除的uri不合法");
        }
    }


    // content://com.example.knowledge_android.android_content_provider.personprovider/person   不根据id更新
    // content://com.example.knowledge_android.android_content_provider.personprovider/person/3 根据id更新
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        //得到连接对象
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        //1.匹配uri,返回code
        int code = matcher.match(uri);
        //如果合法，进行更新
        if (code == 1) { //不根据id更新 (这个1对应，上边static代码块中声明的1)
            int updateCount = database.update("person", contentValues, s, strings);
            database.close();
            return updateCount;
        } else if (code == 2) { //根据id更新 (这个2对应，上边static代码块中声明的2)
            //得到id
            long id = ContentUris.parseId(uri);
            int updateCount = database.update("person", contentValues,"_id=" + id, null);
            database.close();
            return updateCount;
        } else {
            throw new RuntimeException("更新的uri不合法");
        }
    }
}
