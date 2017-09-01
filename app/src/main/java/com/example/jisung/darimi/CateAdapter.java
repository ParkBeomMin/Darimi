package com.example.jisung.darimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeongjiseong on 2017. 8. 7..
 */

public class CateAdapter extends BaseAdapter {
    private ArrayList<Categol> list;
    private Context context;
    public CateAdapter(ArrayList<Categol> list, Context context) {
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
            view = inflater.inflate(R.layout.cate_list, null);
        TextView name = (TextView)view.findViewById(R.id.cate_name);
        if(list.get(i).isChoose()) {
            name.setBackgroundColor(view.getResources().getColor(R.color.White));
            name.setTextColor(view.getResources().getColor(R.color.Key));
        }
        else {
            name.setBackgroundColor(view.getResources().getColor(R.color.Key));
            name.setTextColor(view.getResources().getColor(R.color.White));

        }
            name.setText(list.get(i).getCate_name());
        return view;
    }
}
