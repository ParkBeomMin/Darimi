package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jeongjiseong on 2017. 8. 7..
 */

public class Categol {

    private String cate_name;
    private int id;
    private boolean choose =false;

    public Categol(String cate_name, int id, boolean choose) {
        this.cate_name = cate_name;
        this.id = id;
        this.choose = choose;
    }


    public Categol() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categol(String cate_name, boolean choose) {
        this.cate_name = cate_name;
        this.choose = choose;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
