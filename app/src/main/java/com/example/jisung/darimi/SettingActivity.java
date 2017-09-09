package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;

public class SettingActivity extends AppCompatActivity {
    String time;
    TextView time_N, c_all, c_pro, c_com, c_ext;
    Intent intent;
    GridView work_list_view;
    ExpandableListView work_name_view;
    ArrayList<work_date_list> date_list;
    ArrayList<Order> allWork;
    ArrayList<Order> works;
    work_itemAdapter work_adapter;
    work_nameAdapter nameAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

    }

    void init() {
        realm = Realm.getDefaultInstance();
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        c_all = (TextView) findViewById(R.id.class_all);
        c_pro = (TextView) findViewById(R.id.class_proceed);
        c_com = (TextView) findViewById(R.id.class_comple);
        c_ext = (TextView) findViewById(R.id.class_extire);
        time_N.setText(time);

        allWork = new ArrayList<Order>(realm.where(Order.class).findAll());

        work_list_view = (GridView) findViewById(R.id.work_list);
        work_name_view = (ExpandableListView)findViewById(R.id.work_name_list);

        works = new ArrayList<Order>();
        for(int i=0;i<allWork.size();i++){
            works.add(allWork.get(i));
        }
        date_list = new ArrayList<work_date_list>();
//        workToname();
        work_date_list test = new work_date_list();
        ArrayList<Order> orders = new ArrayList<Order>();
        orders.add(new Order("cfasd;klf","jisug","0100000","2017.09.09",1,true));
        test.setDate("2017.09.09");
        test.setOrders(orders);
        date_list.add(test);
        work_adapter = new work_itemAdapter(works, this);
        work_adapter.realm=realm;
        work_list_view.setAdapter(work_adapter);

        nameAdapter = new work_nameAdapter(date_list,this);
        work_name_view.setAdapter(nameAdapter);
    }
    public void workToname(){
        Log.d("test1","ss");
        Collections.sort(works,new sortWorks());
        String tmp=works.get(0).getDate().substring(0,10);
        for(int i=0;i< works.size();i++){
            Log.d("test1",tmp);
            work_date_list data = new work_date_list();
            ArrayList<Order> orders = new ArrayList<Order>();
            while(tmp.equals(works.get(i).getDate())){
                Log.d("test1",tmp);
                tmp = works.get(i).getDate().substring(0,10);
                data.setDate(tmp);
                orders.add(works.get(i));
                i++;
                if(i==works.size())
                    break;
            }
            data.setOrders(orders);
            date_list.add(data);
            Log.d("test1",data.getDate());
            if(i==works.size())
                break;
        }
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
                works.clear();
                for(int i=0;i<allWork.size();i++){
                    works.add(allWork.get(i));
                }
                work_adapter.notifyDataSetChanged();
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
                works.clear();
                for(int i=0;i<allWork.size();i++){
                    if(allWork.get(i).getWork_state()==0)
                        works.add(allWork.get(i));
                }work_adapter.notifyDataSetChanged();
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
                works.clear();
                for(int i=0;i<allWork.size();i++){
                    if(allWork.get(i).getWork_state()==1)
                        works.add(allWork.get(i));
                }work_adapter.notifyDataSetChanged();

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
    class sortWorks implements Comparator<Order>{

    @Override
    public int compare(Order o1, Order o2) {
        return String.valueOf(o1.getDate()).compareTo(o2.getDate() + "");
    }
}
}
