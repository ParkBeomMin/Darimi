package com.example.jisung.darimi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by parkbeommin on 2017. 8. 3..
 */

public class Custom extends RealmObject{
//    @PrimaryKey
    String id;
    String num, name, call;
    public Custom(){

    }
    public Custom(String id, String num, String name, String call) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.call = call;
    }

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getCall() {
        return call;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCall(String call) {
        this.call = call;
    }
}