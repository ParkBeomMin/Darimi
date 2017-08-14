package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class Sales {
    String date;
    int sale;
    ArrayList<Sales> sublist = new ArrayList<Sales>();
    ArrayList<ArrayList<Sales>> subsublist = new ArrayList<ArrayList<Sales>>();

    public Sales(String date, int sale) {
        this.date = date;
        this.sale = sale;
    }
}
