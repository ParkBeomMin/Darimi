package com.example.jisung.darimi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class ManageActivity extends AppCompatActivity {
    // basis
    Intent intent;
    String time;
    TextView time_N;
    //

    EditText custom_search_edt;
    ListView custom_list;
    ArrayList<Custom> arrayList = new ArrayList<Custom>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        init();
    }

    //basis
    void init(){

        //basis
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView)findViewById(R.id.time);
        time_N.setText(time);
        //

        custom_search_edt = (EditText)findViewById(R.id.custom_search_edt);
        custom_list = (ListView)findViewById(R.id.custom_list);
        adapter = new CustomAdapter(arrayList, this);
        custom_list.setAdapter(adapter);

    }
    public void onClick(View v){
        switch (v.getId()){
            //basis
            case R.id.orderA:
                intent = new Intent(this,OrderActivity.class);
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
            //
            case  R.id.add_custom_btn:
                LayoutInflater inflater = getLayoutInflater();
                final View add_custom = inflater.inflate(R.layout.add_custom, null);
                final EditText add_custom_name_edt = (EditText) add_custom.findViewById(R.id.add_custom_name_edt);
                final EditText add_custom_call_edt = (EditText) add_custom.findViewById(R.id.add_custom_call_edt);
                final Button add_custom_cancel_btn = (Button) add_custom.findViewById(R.id.add_custom_cancel_btn);
                final Button add_custom_confirm_btn = (Button) add_custom.findViewById(R.id.add_custom_confirm_btn);
                final AlertDialog dialog = new AlertDialog.Builder(ManageActivity.this).create();
                dialog.setView(add_custom);
                dialog.show();
                add_custom_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                add_custom_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String custom_name = add_custom_name_edt.getText().toString();
                        String custom_call = add_custom_call_edt.getText().toString();
                        Custom new_Custom = new Custom("1",custom_name, custom_call);
                        arrayList.add(new_Custom);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            default:
                break;

        }
    }
    //

    //beom
    public void search_Click(View v) {
        switch (v.getId()){
            case R.id.all_search_btn:

                break;
            case R.id.r_search_btn:

                break;
            case R.id.s_search_btn:

                break;
            case R.id.e_search_btn:

                break;
            case R.id.f_search_btn:

                break;
            case R.id.a_search_btn:

                break;
            case R.id.q_search_btn:

                break;
            case R.id.t_search_btn:

                break;
            case R.id.d_search_btn:

                break;
            case R.id.w_search_btn:

                break;
            case R.id.c_search_btn:

                break;
            case R.id.z_search_btn:

                break;
            case R.id.x_search_btn:

                break;
            case R.id.v_search_btn:

                break;
            case R.id.g_search_btn:

                break;
        }

    }

}
