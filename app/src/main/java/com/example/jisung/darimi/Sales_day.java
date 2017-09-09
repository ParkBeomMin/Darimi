package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 9. 9..
 */

public class Sales_day {
    String date;
    ArrayList<Sales_custom> arrayList;

    public Sales_day(String date) {
        this.date = date;
//        this.arrayList = arrayList;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Sales_custom> getArrayList() {
        return arrayList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setArrayList(ArrayList<Sales_custom> arrayList) {
        this.arrayList = arrayList;
    }
}
