package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 9. 9..
 */

public class Sales_month {
    String month;
    ArrayList<Sales_day> arrayList;

    public Sales_month(String month, ArrayList<Sales_day> arrayList) {
        this.month = month;
        this.arrayList = arrayList;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setArrayList(ArrayList<Sales_day> arrayList) {
        this.arrayList = arrayList;
    }

    public String getMonth() {
        return month;
    }

    public ArrayList<Sales_day> getArrayList() {
        return arrayList;
    }
}
