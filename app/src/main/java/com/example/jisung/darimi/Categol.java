package com.example.jisung.darimi;

import java.util.ArrayList;

/**
 * Created by jeongjiseong on 2017. 8. 7..
 */

public class Categol {
    private String cate_name;
    private boolean choose;
    private ArrayList<Item> itemlist;

    public Categol(String cate_name, boolean choose, ArrayList<Item> itemlist) {
        this.cate_name = cate_name;
        this.choose = choose;
        this.itemlist = itemlist;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public ArrayList<Item> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<Item> itemlist) {
        this.itemlist = itemlist;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public Categol(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_name() {
        return cate_name;
    }

}
