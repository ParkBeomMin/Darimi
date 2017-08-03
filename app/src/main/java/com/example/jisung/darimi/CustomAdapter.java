package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 3..
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<Custom> arrayList;
    Context c;

    public CustomAdapter(ArrayList<Custom> arrayList, Context c) {
        this.arrayList = arrayList;
        this.c = c;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if (view == null) {
            view = inflater.inflate(R.layout.custom_list, null);
        }
        TextView num = (TextView)view.findViewById(R.id.custom_list_num_tv);
        TextView name = (TextView)view.findViewById(R.id.custom_list_name_tv);
        TextView call = (TextView)view.findViewById(R.id.custom_list_call_tv);

Custom one;
        one = arrayList.get(i);

        num.setText(one.num);
        name.setText(one.name);
        call.setText(one.call);
        return view;
    }
}
