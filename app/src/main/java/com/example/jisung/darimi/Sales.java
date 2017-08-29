package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class Sales {
    String id;
    String name;
    String item;
    int date;
    int sale;

    public Sales(String id, String name, String item, int date, int sale) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.date = date;
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public int getDate() {
        return date;
    }

    public int getSale() {
        return sale;
    }
}
