package com.example.jisung.darimi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.example.jisung.darimi.R.id.date;

/**
 * Created by jisung on 2017. 9. 1..
 */

public class darimiDataCon {
    public static int itemSeqSet(Context context) {
        SharedPreferences pref = context.getSharedPreferences("seq", context.MODE_PRIVATE);
        int is = pref.getInt("num", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("num", ++is);
        editor.commit();
        return is;
    }

    public static void makeItem(Realm realm, final Context context, final String name, final String price, final byte[] img, final int c_id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName(name);
                item.setPrice(price);
                item.setSeq(itemSeqSet(context));
                item.setImg(img);
                item.setMark(false);
                item.setC_id(c_id);

            }
        });
    }
    public static void deleteItem(Realm realm,final  String name){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.where(Item.class).equalTo("name",name).findFirst();
                item.deleteFromRealm();
            }
        });
    }

    public static void updateItemSeq(Realm realm, final String to, final String from) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item1 = realm.where(Item.class).equalTo("name", to).findFirst();
                Item item2 = realm.where(Item.class).equalTo("name", from).findFirst();
                long tmp = item2.getSeq();
                item2.setSeq(item1.getSeq());
                item1.setSeq(tmp);
            }
        });
    }

    public static void changeItemPostion(Realm realm, final ArrayList<Item> datas, final int start, final int end) {

        if (start < end) {
            for (int i = end; i > start; i--)
                updateItemSeq(realm, datas.get(i).getName(), datas.get(i - 1).getName());
        } else {
            for (int i = start; i > end; i--)
                updateItemSeq(realm, datas.get(i).getName(), datas.get(i - 1).getName());
        }

    }

    public static void makeItems(Realm realm, final Item item, final int i) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Items items = realm.createObject(Items.class);
                items.setItem(item);
                items.setItem_num(i);
            }
        });
    }

    public static void makeCustom(Realm realm, final String id, final String name, final String call) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom custom = realm.createObject(Custom.class);
                custom.setName(name);
                custom.setCall(call);
                custom.setNum("1");
            }
        });
    }


    public static void makeOrder(Realm realm, final String data, final String date, final String call, final String name, final int pay) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("test3", "realmBefore");
                Custom result = realm.where(Custom.class).equalTo("call", call).findFirst();
                Log.d("test3", "realm");
                if (result == null) {
                    Custom custom = realm.createObject(Custom.class, call);
                    custom.setName(name);
                    Log.d("test3", "realmCreate");
                }
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Order order = realm.createObject(Order.class);
                order.setData(data);
                order.setName(name);
                order.setCall(call);
                order.setPay(pay);
                order.setDate(date);
                order.setWork_state(0);
                order.setSending(false);

            }
        });
    }

    public static void updateStateOrder(Realm realm, final String date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Order> result = realm.where(Order.class).equalTo("date", date).findAll();
                result.get(0).setWork_state(1);
            }
        });
    }

    public static String findClientName(Realm realm, final String num) {
        final String[] name = new String[1];
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom custom = realm.where(Custom.class).equalTo("call", num).findFirst();
                if (custom == null)
                    name[0] = "";
                else
                    name[0] = custom.getName();
            }
        });
        return name[0];
    }
    public static void insertuserData(Realm realm,final String name, final String call) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom user = realm.createObject(Custom.class, call);
                user.setName(name);
                realm.insert(user);
            }
        });
    }
    public static boolean searchUsercall(Realm realm,final String call){
        final Boolean[] result = {false};
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom user = realm.where(Custom.class).equalTo("call",call).findFirst();
                if(user == null)
                    result[0] =true;
            }
        });
        return result[0];
    }


    public static ArrayList<String> findClientCall(Realm realm, final String name) {
        final ArrayList<String> nums = new ArrayList<String>();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Custom> customs = realm.where(Custom.class).equalTo("name", name).findAll();
                for (int i = 0; i < customs.size(); i++)
                    nums.add(customs.get(i).getCall());
            }
        });
        return nums;
    }


    public static void updatePayOrder(Realm realm, final String date, final int pay) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Order result = realm.where(Order.class).equalTo("date", date).findFirst();
                result.setPay(pay);
            }
        });
    }

    public static void updateMsgOrder(Realm realm, final String date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Order> result = realm.where(Order.class).equalTo("date", date).findAll();
                result.get(0).setSending(true);
            }
        });
    }

    public static void removeOrder(Realm realm, final String date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Order> result = realm.where(Order.class).equalTo("date", date).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public static void makeSales(Realm realm, final String date, final String name, final int price, final int pay) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("BEOM30", "date : " + date);
                Log.d("BEOM30", "name : " + name);
                Log.d("BEOM30", "price : " + price);
                Log.d("BEOM30", "pay : " + pay);
                Sales sales = realm.createObject(Sales.class, date);
                sales.setName(name);
                sales.setPay(pay);
                sales.setSum(price);
            }
        });
    }
}
