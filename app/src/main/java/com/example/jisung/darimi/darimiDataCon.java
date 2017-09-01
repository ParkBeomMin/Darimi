package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jisung on 2017. 9. 1..
 */

public class darimiDataCon {
//    public static void makeItem(Realm realm,){}
//    public static void makeCustom(Realm realm,){}
    public static void makeOrder(Realm realm, final RealmList<Items> data, final Custom custom, final String date){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Order order = realm.createObject(Order.class);
                order.setData(data);
                order.setCustom(custom);
                order.setDate(date);
                order.setWork_state(0);
                order.setSending(false);

            }
        });

    }
}
