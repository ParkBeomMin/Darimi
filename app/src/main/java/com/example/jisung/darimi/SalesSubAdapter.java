package com.example.jisung.darimi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class SalesSubAdapter extends BaseAdapter {
    ArrayList<Sales> arrayList;
    Context c;

    public SalesSubAdapter(ArrayList<Sales> arrayList, Context c) {
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
        if(view == null) {
            view = inflater.inflate(R.layout.sublist, null);
        }
        TextView name = (TextView)view.findViewById(R.id.sublist_name_tv);
        TextView sale = (TextView)view.findViewById(R.id.sublist_sale_tv);
        Log.d("BEOM3", "size = " + getCount());
        Sales one;
        one = arrayList.get(i);
        Log.d("BEOM3", "date = " + getItem(i).toString());
        name.setText(one.date);
        sale.setText(""+one.sale);
        return view;
    }
}
