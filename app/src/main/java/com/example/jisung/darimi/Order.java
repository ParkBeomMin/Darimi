package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by jisung on 2017. 8. 30..
 */

public class Order {
    private ArrayList<Items> data;
    private Custom custom;
    private String date;

    public Order(ArrayList<Items> data, Custom custom, String date) {
        this.data = data;
        this.custom = custom;
        this.date = date;
    }

    public ArrayList<Items> getData() {
        return data;
    }

    public void setData(ArrayList<Items> data) {
        this.data = data;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getOrderPrice(){
        int result=0;
        for(int i=0;i<data.size();i++){
            result+=data.get(i).getTotalprice();
        }
        return result;
    }
}
