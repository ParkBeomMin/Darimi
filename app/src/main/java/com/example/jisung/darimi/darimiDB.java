package com.example.jisung.darimi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jisung on 2017. 8. 8..
 */

public class darimiDB extends SQLiteOpenHelper {
    public darimiDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists item(" +
                "name text, " +
                "price text," +
                "img integer," +
                "mark boolean)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table if not exists custom(" +
                "num text, " +
                "name text," +
                "call text)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table if not exists order(" +
                "client blob, " +
                "item blob," +
                "date date," +
                "state integer)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists item";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}