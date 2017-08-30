package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by jisung on 2017. 8. 29..
 */

public class work_item {
    private String date;
    private String name;
    private ArrayList<SelectItem> items;
    private int work_state;
    private boolean sending;

    public work_item(String date, String name, ArrayList<SelectItem> items, int work_state, boolean sending) {
        this.date = date;
        this.name = name;
        this.items = items;
        this.work_state = work_state;
        this.sending = sending;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SelectItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<SelectItem> items) {
        this.items = items;
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
}
