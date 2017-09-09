package com.example.jisung.darimi;

/**
 * Created by parkbeommin on 2017. 9. 9..
 */

public class Sales_custom {
    String date;
    String name;
    String price;

    public Sales_custom(String date, String name, String price) {
        this.date = date;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
