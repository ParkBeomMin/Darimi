package com.example.jisung.darimi;

import io.realm.Realm;

/**
 * Created by jisung on 2017. 9. 1..
 */

public class darimiDataInit {
    public static void categolDataInit(Realm realm){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(true);
                cate.setCate_name("즐겨찾기");
                cate.setId(0);
            }

        });        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("상의");
                cate.setId(1);
            }

        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("하의");
                cate.setId(2);
            }

        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("겉옷");
                cate.setId(3);
            }

        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("정장");
                cate.setId(4);
            }

        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("신발");
                cate.setId(5);
            }

        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Categol cate = realm.createObject(Categol.class);
                cate.setChoose(false);
                cate.setCate_name("기타");
                cate.setId(6);
            }

        });




    }

    public static void itemDataInit(Realm realm){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("맨투맨");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.images);
                item.setMark(true);
                item.setC_id(1);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("티셔츠");
                item.setSeq(2);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(1);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("청바지");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(2);
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("반바지");
                item.setSeq(2);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(2);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("치마");
                item.setSeq(3);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(2);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("츄리닝");
                item.setSeq(4);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(2);
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("셔츠");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(3);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("정장바지");
                item.setSeq(2);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(3);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("마이");
                item.setSeq(3);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(3);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("패딩");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(4);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("야상");
                item.setSeq(2);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(true);
                item.setC_id(4);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("구두");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.images1);
                item.setMark(false);
                item.setC_id(5);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("운동화");
                item.setSeq(2);
                item.setPrice("10000");
                item.setImg(R.drawable.images2);
                item.setMark(false);
                item.setC_id(5);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("워커");
                item.setSeq(3);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(false);
                item.setC_id(5);
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class);
                item.setName("넥타이");
                item.setSeq(1);
                item.setPrice("10000");
                item.setImg(R.drawable.imgnull);
                item.setMark(false);
                item.setC_id(6);
            }
        });




    }
//    public static void orderDataTest(Realm realm){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Item item = realm.createObject(Item.class);
//                item.setName("맨투맨");
//                item.setSeq(1);
//                item.setPrice("5000");
//                item.setImg(R.drawable.images);
//                item.setMark(true);
//                item.setC_id(1);
//
//                items.setItem(item);
//                items.setItem_num(2);
//                Custom custom =realm.createObject(Custom.class);
//                custom.setName("jisung");
//                custom.setCall("01089523212");
//                custom.setId("23123");
//                Order order = realm.createObject(Order.class);
//                order.setWork_state(1);
//                order.setCustom(custom);
//                order.getData().add(items);
//                order.setDate("2017-05-23");
//                order.setSending(false);
//            }
//        });
//    }

}
