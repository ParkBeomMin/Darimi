package com.example.jisung.darimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView time_N,c_num,p_num;
    Intent intent;
    Button searchBtn;
    EditText nameE;
    ListView work_list_view,Awork_list_view;
    ExpandableListView work_name_view;
    ArrayList<work_date_list> date_list;
    ArrayList<Order> allWork;
    ArrayList<Order> Bworks,Aworks;
    work_itemAdapter work_adapter,Awork_adapter;
    work_nameAdapter nameAdapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameE.getText().toString();
                work_adapter.sortToName(name);
                Awork_adapter.sortToName(name);
            }
        });

    }

    void init() {
        realm = Realm.getDefaultInstance();
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        nameE = (EditText)findViewById(R.id.nameSearch);

        c_num = (TextView)findViewById(R.id.c_num);
        p_num = (TextView)findViewById(R.id.p_num);

        time_N.setText(time);
        searchBtn =(Button)findViewById(R.id.search_btn);
        allWork = new ArrayList<Order>(realm.where(Order.class).findAll());

        work_list_view = (ListView) findViewById(R.id.work_list);
        Awork_list_view =(ListView) findViewById(R.id.Bwork_list);
        work_name_view = (ExpandableListView) findViewById(R.id.work_name_list);

        Bworks = new ArrayList<Order>();
        Aworks= new ArrayList<Order>();
        for (int i = 0; i < allWork.size(); i++) {
            if(allWork.get(i).getWork_state()==0)
                Bworks.add(allWork.get(i));
            else
                Aworks.add(allWork.get(i));
        }
        date_list = new ArrayList<work_date_list>();
        workToname();

        work_adapter = new work_itemAdapter(Bworks, this);
        work_adapter.realm = realm;
        work_adapter.nextlist = Aworks;
        Awork_adapter = new work_itemAdapter(Aworks,this);
        Awork_adapter.realm=realm;


        work_list_view.setAdapter(work_adapter);
        Awork_list_view.setAdapter(Awork_adapter);

        work_adapter.txt = p_num;
        Awork_adapter.txt = c_num;

        nameAdapter = new work_nameAdapter(date_list, this);
        nameAdapter.Bworks = Bworks;
        nameAdapter.Aworks = Aworks;
        nameAdapter.Aadapter=Awork_adapter;
        nameAdapter.Badapter=work_adapter;
        nameAdapter.Alls =allWork;
        Awork_adapter.noti = nameAdapter;
        work_name_view.setAdapter(nameAdapter);


    }

    public void workToname() {
        Collections.sort(allWork, new sortWorks());
        for (int i = 0; i < allWork.size(); i++) {
            Log.d("test2",allWork.get(i).getDate());
        }
        String tmp="";
        if(allWork.size()!=0)
            tmp = allWork.get(0).getDate().substring(0,8);
        for (int i = 0; i < allWork.size(); i++) {
            Log.d("test1", tmp);
            work_date_list data = new work_date_list();
            ArrayList<Order> orders = new ArrayList<Order>();
            while (tmp.equals(allWork.get(i).getDate().substring(0,8))) {
                Log.d("test1", tmp);
                tmp = allWork.get(i).getDate().substring(0,8);
                data.setDate(tmp);
                orders.add(allWork.get(i));
                i++;
                if (i == allWork.size())
                    break;
            }
            data.setOrders(orders);
            date_list.add(data);
            Log.d("test1", data.getDate());
            if (i == allWork.size())
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


    class sortWorks implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return String.valueOf(o1.getDate()).compareTo(o2.getDate() + "");
        }
    }
}
