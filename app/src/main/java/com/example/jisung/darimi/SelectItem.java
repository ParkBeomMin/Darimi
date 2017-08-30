package com.example.jisung.darimi;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class SelectItem {
    private Item item;
    private int num;
    private int totoal_price;

    public SelectItem(Item item, int num, int totoal_price) {
        this.item = item;
        this.num = num;
        this.totoal_price = totoal_price;
    }

    public int getTotoal_price() {
        return totoal_price;
    }

    public void setTotoal_price(int totoal_price) {
        this.totoal_price = totoal_price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
