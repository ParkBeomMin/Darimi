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

public class ItemAdapter extends BaseAdapter {

    ArrayList<Item> list;
    Context context;

    public ItemAdapter(ArrayList<Item> list, Context context) {
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
            view = inflater.inflate(R.layout.order_item, null);


        TextView name =(TextView)view.findViewById(R.id.item_name);
        TextView price = (TextView)view.findViewById(R.id.item_price);
        ImageView img = (ImageView)view.findViewById(R.id.item_img);
        ImageView mark = (ImageView)view.findViewById(R.id.item_mark);

        name.setText(list.get(i).getName());
        price.setText(list.get(i).getPrice());
        img.setImageResource(list.get(i).getImg());
        if(list.get(i).isMark())
            mark.setImageResource(R.drawable.item_marked);


        return view;
    }
}
