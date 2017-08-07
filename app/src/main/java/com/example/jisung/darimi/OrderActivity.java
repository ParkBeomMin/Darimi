package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

public class OrderActivity extends AppCompatActivity {
    private TextView time_N;
    private String today_date,today_time;
    private Intent intent;

    private ArrayList<Item> item_list;
    private ArrayList<SelectItem> selectItems_list;
    private ItemAdapter item_adapter;
    private SelectItemAdapter selected_adapter;
    private GridView item_view;
    private ListView cate_view,sele_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        getObjectData();
    }
    void init(){

        time_N = (TextView)findViewById(R.id.time);

        item_list = new ArrayList<Item>();
        selectItems_list = new ArrayList<SelectItem>();

        item_adapter = new ItemAdapter(item_list,this);
        selected_adapter = new SelectItemAdapter(selectItems_list,this);

        item_view = (GridView)findViewById(R.id.item_list);
        sele_view = (ListView)findViewById(R.id.selected_list);

        item_view.setAdapter(item_adapter);
        sele_view.setAdapter(selected_adapter);

        timesetM();


    }


    void getObjectData(){

    }



    void timesetM(){
        nowTime();
        time_N.setText(today_date);
    }

    void onClick(View v){
        switch (v.getId()){
            case R.id.manageA:
                intent = new Intent(this,ManageActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this,SalesActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            case R.id.settingA:
                intent = new Intent(this,SettingActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    void nowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        Time time = new Time(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
//        SimpleTimeZone sdtNow = new SimpleTimeZone()
        String formatDate = sdfNow.format(date);
        today_date =formatDate.substring(0,4)+"년 "+formatDate.substring(5,7)+"월 "+formatDate.substring(8,10)+"일";
    }
}
