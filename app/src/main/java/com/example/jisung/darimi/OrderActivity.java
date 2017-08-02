package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    TextView time_N;
    String time;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
    }
    void init(){
        nowTime();
        time_N = (TextView)findViewById(R.id.time);
        time_N.setText(time);
    }

    void onClick(View v){
        switch (v.getId()){
            case R.id.manageA:
                intent = new Intent(this,ManageActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this,SalesActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
            case R.id.settingA:
                intent = new Intent(this,SettingActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    void nowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdfNow.format(date);
        time =formatDate.substring(0,4)+"년 "+formatDate.substring(5,7)+"월 "+formatDate.substring(8,10)+"일";
    }
}
