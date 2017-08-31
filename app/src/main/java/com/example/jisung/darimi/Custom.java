package com.example.jisung.darimi;

/**
 * Created by parkbeommin on 2017. 8. 3..
 */

public class Custom {
    String id;
    String num, name, call;

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
}
