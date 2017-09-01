package com.example.jisung.darimi;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class SelectItemAdapter extends BaseAdapter {

    ArrayList<Items> list;
    Context context;
    TextView num_Text;
    TextView price_Text;

    public SelectItemAdapter(ArrayList<Items> list, Context context, TextView num_Text, TextView price_Text) {
        this.list = list;
        this.context = context;
        this.num_Text = num_Text;
        this.price_Text = price_Text;
    }

    public SelectItemAdapter(ArrayList<Items> list, Context context) {
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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null)
            view = inflater.inflate(R.layout.selected_item, null);

        final TextView name =(TextView)view.findViewById(R.id.item_name);
        final TextView price = (TextView)view.findViewById(R.id.item_price);
        final TextView num = (TextView)view.findViewById(R.id.item_num);
        final TextView total_price = (TextView)view.findViewById(R.id.item_total_price);
        final LinearLayout setting = (LinearLayout)view.findViewById(R.id.num_setting_layout);
        Button cancel = (Button)view.findViewById(R.id.item_cancel);

        //invisible views
        final TextView item_num_set = (TextView)view.findViewById(R.id.item_num_set);
        Button addBtn = (Button) view.findViewById(R.id.item_add_btn);
        Button subBtn = (Button) view.findViewById(R.id.item_sub_btn);
        Button setExit = (Button)view.findViewById(R.id.set_exit_btn);


        name.setText(list.get(i).getItem().getName());
        price.setText("￦"+list.get(i).getItem().getPrice());
        num.setText(list.get(i).getItem_num()+"");
        item_num_set.setText(list.get(i).getItem_num()+"");
        total_price.setText("￦"+list.get(i).getTotalprice()+"");
        int now=0,Tnum=0;
        for(int j =0;j<list.size();j++){
            now += list.get(j).getTotalprice();
            Tnum += list.get(j).getItem_num();
        }
        price_Text.setText(""+now);
        num_Text.setText(Tnum+"벌");



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setItem_num(list.get(i).getItem_num()+1);
                item_num_set.setText(list.get(i).getItem_num()+"");
                total_price.setText("￦"+list.get(i).getTotalprice()+"");
                num.setText(list.get(i).getItem_num()+"");
                notifyDataSetChanged();
//                int tprice = now + Integer.parseInt(list.get(i).getItem().getPrice());
//                price_Text.setText(""+tprice);
//                OrderTotal.setText(Integer.parseInt(OrderTotal.getText().toString())+Integer.parseInt(list.get(i).getItem().getPrice())+"");


            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getItem_num()>1)
                    list.get(i).setItem_num(list.get(i).getItem_num() - 1);

                item_num_set.setText(list.get(i).getItem_num() +"");
                total_price.setText("￦"+list.get(i).getTotalprice()+"");
                num.setText(list.get(i).getItem_num()+"");
                notifyDataSetChanged();
//                int tprice = now - Integer.parseInt(list.get(i).getItem().getPrice());
//                price_Text.setText(""+tprice);
            }
        });

        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting.setVisibility(View.VISIBLE);
                item_num_set.setText(num.getText().toString());
                notifyDataSetInvalidated();
            }
        });

        setExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting.setVisibility(View.GONE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(i);
//                int tprice = now -list.get(i).getTotalprice();
//                price_Text.setText(""+tprice);
                if(list.size()==0) {
                    price_Text.setText("");
                    num_Text.setText("0벌");
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
