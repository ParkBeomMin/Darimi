package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 9. 9..
 */

public class Sales_year {
    String year;
    ArrayList<Sales_month> arrayList;

    public Sales_year(String year, ArrayList<Sales_month> arrayList) {
        this.year = year;
        this.arrayList = arrayList;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setArrayList(ArrayList<Sales_month> arrayList) {
        this.arrayList = arrayList;
    }

    public String getYear() {
        return year;
    }

    public ArrayList<Sales_month> getArrayList() {
        return arrayList;
    }
}
