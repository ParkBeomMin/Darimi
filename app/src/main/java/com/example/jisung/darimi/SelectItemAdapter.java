package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class SelectItemAdapter extends BaseAdapter {

    ArrayList<SelectItem> list;
    Context context;

    public SelectItemAdapter(ArrayList<SelectItem> list, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null)
            view = inflater.inflate(R.layout.selected_item, null);

        TextView name =(TextView)view.findViewById(R.id.item_name);
        TextView price = (TextView)view.findViewById(R.id.item_price);
        TextView num = (TextView)view.findViewById(R.id.item_num);
        TextView total_price = (TextView)view.findViewById(R.id.item_total_price);

        name.setText(list.get(i).getItem().getName());
        price.setText(list.get(i).getItem().getPrice());
        num.setText(list.get(i).getNum());
        total_price.setText((list.get(i).getNum() *(Integer.parseInt(list.get(i).getItem().getPrice())))+"");

        return view;
    }
}
