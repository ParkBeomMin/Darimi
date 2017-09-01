package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by jisung on 2017. 8. 29..
 */

public class client_itemAdapter extends BaseAdapter
{
    Context context;
    RealmList<Items> list;

    public client_itemAdapter(Context context, RealmList<Items> list) {
        this.context = context;
        this.list = list;
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
            view = inflater.inflate(R.layout.work_client_item, null);
        TextView client = (TextView)view.findViewById(R.id.item_name);
        TextView num = (TextView)view.findViewById(R.id.item_num);
        client.setText(list.get(i).getItem().getName());
        num.setText(list.get(i).getItem_num());
        return view;
    }
}
