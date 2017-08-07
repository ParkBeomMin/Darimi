package com.example.jisung.darimi;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class Item {
    private String name;
    private String price;
    private int img;
    private boolean mark;

    public Item(String name, String price, int img, boolean mark) {
        this.name = name;
        this.price = price;
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
