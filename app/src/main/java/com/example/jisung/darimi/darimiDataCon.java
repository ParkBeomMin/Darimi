package com.example.jisung.darimi;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jisung on 2017. 9. 1..
 */

public class darimiDataCon {
    public static void makeItems(Realm realm, final Item item, final int i){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Items items = realm.createObject(Items.class);
                items.setItem(item);
                items.setItem_num(i);
            }
        });
    }
    public static void makeCustom(Realm realm, final String id, final String name, final String call){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom custom = realm.createObject(Custom.class);
                custom.setId(id);
                custom.setName(name);
                custom.setCall(call);
                custom.setNum("1");
            }
        });
    }


    public static void makeOrder(Realm realm, final RealmList<Items> data, final String date, final Custom custom){
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
