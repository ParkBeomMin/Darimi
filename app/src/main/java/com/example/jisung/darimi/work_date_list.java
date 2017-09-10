package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-09-09.
 */

public class work_date_list {
    private String date;
    private ArrayList<Order> orders;

    public work_date_list(String date) {
        this.date = date;
    }

    public work_date_list() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public work_date_list(String date, ArrayList<Order> orders) {
        this.date = date;
        this.orders = orders;
    }
}
