package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jisung on 2017. 8. 14..
 */

public class ItemBaseAdapter extends BaseAdapter {
    ArrayList<Item> list;
    private Context context;
    Realm realm;

    public ItemBaseAdapter(ArrayList<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ItemBaseAdapter(ArrayList<Item> list, Context context,Realm realm) {
        this.list = list;
        this.context = context;
        this.realm = realm;
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
            view = inflater.inflate(R.layout.order_item, null);


        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView price = (TextView) view.findViewById(R.id.item_price);
        ImageView img = (ImageView) view.findViewById(R.id.item_img);
        ImageView mark = (ImageView) view.findViewById(R.id.item_mark);

        name.setText(list.get(i).getName());
        price.setText(list.get(i).getPrice());
        img.setImageResource(list.get(i).getImg());
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                list.get(i).setMark(!list.get(i).isMark());
                realm.commitTransaction();
                notifyDataSetChanged();
            }
        });

        if (list.get(i).isMark())
            mark.setImageResource(R.drawable.item_marked);
        else
            mark.setImageResource(R.drawable.item_non_mark);


        return view;

    }
}
