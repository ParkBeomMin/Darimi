package com.example.jisung.darimi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jisung on 2017. 8. 8..
 */

public class manageDB {
    private static darimiDB database = null;
    private static SQLiteDatabase myDB = null;
    private static manageDB mInstance = null;

    public manageDB(Context context) {
        database = new darimiDB(context,"myDB",null,1);
        myDB = database.getWritableDatabase();
    }

    public final static  manageDB getInstance(Context context){
        if(mInstance==null)
            mInstance = new manageDB(context);
        return mInstance;
    }

    public ArrayList<Item> selectItem(){
        ArrayList<Item> data = new ArrayList<>();
        String sql = "Select * from item";
        Cursor cursor = myDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(0);
                String price= cursor.getString(1);
                long seq = cursor.getInt(2);
                int img = cursor.getInt(3);
                boolean mark=cursor.getInt(4)>0;
                data.add(new Item(name,price,seq,img,mark));
            }while (cursor.moveToFirst());

        }
        cursor.close();
        return data;
    }
    public Boolean insertItem(Item data){
        String sql = "Insert into item values('";
        sql+=data.getName()+"','";
        sql+=data.getPrice()+"','";
        sql+=data.getSeq()+"','";
        sql+=data.getImg()+"','";
        sql+=data.isMark()+"')";
        myDB.execSQL(sql);
        return true;
    }
    public Boolean updateItem(Item old_d,Item data){
        String sql="Update item Set ";
        sql+="name='"+data.getName();
        sql+="',price='"+data.getPrice();
        sql+="',seq='"+data.getSeq();
        sql+="',img='"+data.getImg();
        sql+="',mark='"+data.isMark();

        sql+="' Where ";
        sql+="name='"+data.getName();
        sql+="',price='"+data.getPrice();
        sql+="',img='"+data.getImg();
        sql+="',mark='"+data.isMark()+"'";

        myDB.execSQL(sql);
        return true;
    }

    public void Insert_Custom(Custom data){
        String sql = "Insert into custom values('";
        sql+=data.getName()+"','";
        sql+=data.getCall()+"')";
        myDB.execSQL(sql);
    }
    public void Get_Custom(){
        ArrayList<Custom> data = new ArrayList<>();
        String sql = "Select * from custom";
        Cursor cursor = myDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String num= cursor.getString(1);
                String name = cursor.getString(2);
                String call = cursor.getString(3);
                data.add(new Custom(id,num,name,call));
            }while (cursor.moveToFirst());

        }
        cursor.close();
    }

}
