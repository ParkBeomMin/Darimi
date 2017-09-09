package com.example.jisung.darimi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class Item extends RealmObject {
//    @PrimaryKey
    private String name;
    private String price;
    private long seq;
    private int img;
    private boolean mark;
    private int c_id;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public Item() {
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }


    public Item(String name, String price, long seq, int img, boolean mark) {
        this.name = name;
        this.price = price;
        this.seq = seq;
        this.img = img;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
