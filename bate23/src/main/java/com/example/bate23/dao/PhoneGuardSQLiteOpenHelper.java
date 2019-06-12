package com.example.bate23.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PhoneGuardSQLiteOpenHelper extends SQLiteOpenHelper {
    public PhoneGuardSQLiteOpenHelper(Context context) {
        super(context, "firewall", null, 1);
    }
    //为具体操作的实体类构建底层表结构并且指定字段类型及属性
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE dark(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, name TEXT NOT NULL, phonenumber TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE regularDark(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, name TEXT NOT NULL, phonenumber TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE duanxin(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, phonenumber TEXT Not Null, content TEXT, time TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE tonghua(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, phonenumber TEXT Not Null, time TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE setting(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, smart INTEGER NOT NULL,global INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}