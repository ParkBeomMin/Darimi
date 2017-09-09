package com.example.jisung.darimi;

/**
 * Created by jisung on 2017. 9. 9..
 */

public class dateSet {
    public static String b_date(String date){
        String result="";
        result+=date.substring(0,4)+"."+date.substring(4,6)+"."+date.substring(6,8);
        return result;
    }
}
