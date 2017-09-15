package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jisung on 2017. 8. 30..
 */

public class Order extends RealmObject{
    private String data;//
    private String name;
    private String call;
    private String date;
    private int work_state;
    private boolean sending;
    private int pay;

    public Order() {
    }

    public Order(String data, String name, String call, String date, int work_state, boolean sending) {
        this.data = data;
        this.name = name;
        this.call =call;
        this.date = date;
        this.work_state = work_state;
        this.sending = sending;
    }

    public int getWork_state() {
        return work_state;
    }

    public void setWork_state(int work_state) {
        this.work_state = work_state;
    }

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getOrderPrice(){
        int result=0;
        ArrayList<item_basic> d;
        d = itemParser.parserString(data);
        for(int i=0;i<d.size();i++){
            result+=Integer.parseInt(d.get(i).getPrice());
        }
        return result;
    }
}
