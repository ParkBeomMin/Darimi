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
                int img = cursor.getInt(2);
                boolean mark=cursor.getInt(3)>0;
                data.add(new Item(name,price,img,mark));
            }while (cursor.moveToFirst());

        }
        cursor.close();
        return data;
    }
    public Boolean insertItem(Item data){
        String sql = "Insert into item values('";
        sql+=data.getName()+"','";
        sql+=data.getPrice()+"','";
        sql+=data.getImg()+"','";
        sql+=data.isMark()+"')";
        myDB.execSQL(sql);
        return true;
    }
    public Boolean updateItem(Item old_d,Item data){
        String sql="Update item Set ";
        sql+="name='"+data.getName();
        sql+="',price='"+data.getPrice();
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

}
