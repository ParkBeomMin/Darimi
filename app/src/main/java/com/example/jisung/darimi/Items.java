package com.example.jisung.darimi;

import io.realm.RealmObject;

/**
 * Created by jisung on 2017. 8. 30..
 */

public class Items extends RealmObject {
    private Item item;
    private int item_num;

    public Items() {
    }

    public Items(Item item, int item_num) {
        this.item = item;
        this.item_num = item_num;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }
    public int getTotalprice(){
        return Integer.parseInt(item.getPrice())*item_num;
    }
}
