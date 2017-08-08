package com.example.jisung.darimi;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class SelectItem {
    private Item item;
    private int num;

    public SelectItem(Item item, int num) {
        this.item = item;
        this.num = num;
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
