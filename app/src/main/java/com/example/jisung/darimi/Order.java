package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jisung on 2017. 8. 30..
 */

public class Order extends RealmObject{
    private RealmList<Items> data;
    private Custom custom;
    private String date;
    private int work_state;
    private boolean sending;

    public Order() {
    }

    public Order(RealmList<Items> data, Custom custom, String date, int work_state, boolean sending) {
        this.data = data;
        this.custom = custom;
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

    public RealmList<Items> getData() {
        return data;
    }

    public void setData(RealmList<Items> data) {
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
