package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 9. 5..
 */

public class Test1 {
    String date;
    ArrayList<Test2> arrayLists;

    public Test1(String date, ArrayList<Test2> arrayLists) {
        this.date = date;
        this.arrayLists = arrayLists;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Test2> getArrayLists() {
        return arrayLists;
    }
}
