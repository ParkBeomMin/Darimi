package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SalesActivity extends AppCompatActivity {
    Intent intent;
    TextView time_N;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
    }
    void init(){
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
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
            case R.id.orderA:
                intent = new Intent(this,OrderActivity.class);
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
}
