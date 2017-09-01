package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;

public class SettingActivity extends AppCompatActivity {
    String time;
    TextView time_N, c_all, c_pro, c_com, c_ext;
    Intent intent;
    GridView work_list_view;
    ArrayList<Order> works;
    work_itemAdapter work_adapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

    }

    void init() {
//        Realm.init(this);
        realm = Realm.getDefaultInstance();
//        darimiDataInit.orderDataTest(realm);
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        c_all = (TextView) findViewById(R.id.class_all);
        c_pro = (TextView) findViewById(R.id.class_proceed);
        c_com = (TextView) findViewById(R.id.class_comple);
        c_ext = (TextView) findViewById(R.id.class_extire);
        time_N.setText(time);

        work_list_view = (GridView) findViewById(R.id.work_list);
        works = new ArrayList<Order>(realm.where(Order.class).findAll());

        work_adapter = new work_itemAdapter(works, this);
        work_list_view.setAdapter(work_adapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manageA:
                intent = new Intent(this, ManageActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;
            case R.id.orderA:
                intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this, SalesActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void work_onclick(View view) {
        switch (view.getId()) {
            case R.id.class_all:
                c_all.setBackground(getDrawable(R.drawable.round_btn_key2));
                c_pro.setBackgroundColor(getColor(R.color.White));
                c_com.setBackgroundColor(getColor(R.color.White));
                c_ext.setBackgroundColor(getColor(R.color.White));
                c_all.setTextColor(getColor(R.color.White));
                c_pro.setTextColor(getColor(R.color.text));
                c_com.setTextColor(getColor(R.color.text));
                c_ext.setTextColor(getColor(R.color.text));
//                works = loadList(0);
                break;
            case R.id.class_proceed:
                c_all.setBackgroundColor(getColor(R.color.White));
                c_pro.setBackground(getDrawable(R.drawable.round_btn_key2));
                c_pro.setTextColor(getColor(R.color.White));
                c_com.setBackgroundColor(getColor(R.color.White));
                c_ext.setBackgroundColor(getColor(R.color.White));
                c_all.setTextColor(getColor(R.color.text));
                c_pro.setTextColor(getColor(R.color.White));
                c_com.setTextColor(getColor(R.color.text));
                c_ext.setTextColor(getColor(R.color.text));
//                works = loadList(1);
                break;
            case R.id.class_comple:
                c_all.setBackgroundColor(getColor(R.color.White));
                c_pro.setBackgroundColor(getColor(R.color.White));
                c_com.setBackground(getDrawable(R.drawable.round_btn_key2));
                c_com.setTextColor(getColor(R.color.White));
                c_ext.setBackgroundColor(getColor(R.color.White));
                c_all.setTextColor(getColor(R.color.text));
                c_pro.setTextColor(getColor(R.color.text));
                c_com.setTextColor(getColor(R.color.White));
                c_ext.setTextColor(getColor(R.color.text));

//                works = loadList(2);
                break;
            case R.id.class_extire:
                c_all.setBackgroundColor(getColor(R.color.White));
                c_pro.setBackgroundColor(getColor(R.color.White));
                c_com.setBackgroundColor(getColor(R.color.White));
                c_ext.setBackground(getDrawable(R.drawable.round_btn_key2));
                c_all.setTextColor(getColor(R.color.text));
                c_pro.setTextColor(getColor(R.color.text));
                c_com.setTextColor(getColor(R.color.text));
                c_ext.setTextColor(getColor(R.color.White));
//                works = loadList(3);
                break;
            default:
                break;
        }
    }
//    public ArrayList<Order> loadList(int i){
//        ArrayList<Order> list;
//        list = new ArrayList<Order>();
//        switch (i){
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//        }
//        System.gc();
//        return list;
//    }
}
