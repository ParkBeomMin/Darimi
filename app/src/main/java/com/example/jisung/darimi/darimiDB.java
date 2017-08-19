package com.example.jisung.darimi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
                "name varchar(12), " +
                "price varchar(12)," +
                "img number," +
                "mark boolean)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table if not exists custom(" +
                "id number not null, " +
                "num integer primary key autoincrement, " +
                "name varchar(12)," +
                "call varchar(15))";
        sqLiteDatabase.execSQL(sql);


//        sql = "create table if not exists order(" +
//                "custom blob, " +
//                "item blob," +
//                "date date," +
//                "state number)";
//        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists item;";
        sql += "drop table if exists custom;";
        sql += "drop table if exists order;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void Insert_Custom(ArrayList<Custom> arrayList, Custom data) {
        SQLiteDatabase myDB = getWritableDatabase();

        String sql = "Insert into custom values('";
        sql += data.getId() + "',";
        sql += "null,'";
        sql += data.getName() + "','";
        sql += data.getCall() + "')";
        myDB.execSQL(sql);
        arrayList.add(data);
    }

    public void Get_Custom(ArrayList<Custom> arrayList) {
        arrayList.clear();
        SQLiteDatabase myDB = getWritableDatabase();
//        ArrayList<Custom> data = new ArrayList<>();
        String sql = "Select * from custom";
        Cursor cursor = myDB.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String num = cursor.getString(1);
            String name = cursor.getString(2);
            String call = cursor.getString(3);
            arrayList.add(new Custom(id, num, name, call));
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).num = Integer.toString(i + 1);
            }
        }
//        if (cursor.moveToFirst()) {
//            do {
//                String num = cursor.getString(0);
//                String name = cursor.getString(1);
//                String call = cursor.getString(2);
//                arrayList.add(new Custom(num, name, call));
//            } while (cursor.moveToFirst());
//        }
        cursor.close();
    }

    public void Update_Custom(String id, String name, String call) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE custom SET name='" + name + "' WHERE id='" + id + "';");
        db.execSQL("UPDATE custom SET call='" + call + "' WHERE id='" + id + "';");
        db.close();
    }

    public void Delete_Custom(String id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM custom WHERE id='" + id + "';");
        db.close();
    }


}