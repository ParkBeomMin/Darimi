package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by jisung on 2017. 8. 29..
 */

public class work_itemAdapter extends BaseAdapter {

    ArrayList<Order> list;
    Context context;
    boolean isAll=false;


    public work_itemAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null)
            view = inflater.inflate(R.layout.work_item_list, null);


        TextView date = (TextView)view.findViewById(R.id.work_date);
        TextView client = (TextView)view.findViewById(R.id.client_name);
        ImageButton msgBtn = (ImageButton)view.findViewById(R.id.send_msg);
        ListView work_list=(ListView)view.findViewById(R.id.work_item_list);
        ImageView work_state = (ImageView)view.findViewById(R.id.work_state);
        Button comBtn = (Button)view.findViewById(R.id.work_comp);
        Button recepitBtn = (Button)view.findViewById(R.id.work_recepit);

        client_itemAdapter adapter = new client_itemAdapter(view.getContext(),itemParser.parserString(list.get(i).getData()));

        client.setText(list.get(i).getName());
        date.setText(list.get(i).getDate());
//        client.setText(list.get(i).getCustom().getName());
        if(list.get(i).isSending())
//            msgBtn.setImageResource();

        work_list.setAdapter(adapter);
        switch (list.get(i).getWork_state()){
            case 1:
                break;
            case 2:
                comBtn.setClickable(false);
//                comBtn.setBackground();
                break;
            case 3:
                comBtn.setClickable(false);
                recepitBtn.setClickable(false);
//                comBtn.setBackground();
//                recepitBtn.setBackground();
                break;
            default:
                break;
        }
        comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setWork_state(1);
                if(!isAll)
                    list.remove(i);
                notifyDataSetChanged();
            }
        });
        recepitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setWork_state(2);
                if(!isAll)
                    list.remove(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
