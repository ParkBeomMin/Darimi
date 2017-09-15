package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class Sales extends RealmObject{
@PrimaryKey
    private String date;
    private String name;
    private int sum;
    private int pay;

    public Sales() {
    }

    public Sales(String date, String name, int sum, int pay) {
        this.date = date;
        this.name = name;
        this.sum = sum;
        this.pay = pay;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public int getPay() {
        return pay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }
}
