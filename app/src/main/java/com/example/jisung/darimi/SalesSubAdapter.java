package com.example.jisung.darimi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class SalesSubAdapter extends BaseExpandableListAdapter {
    ArrayList<Sales> arrayList;
    Context c;

    public SalesSubAdapter(ArrayList<Sales> arrayList, Context c) {
        this.arrayList = arrayList;
        this.c = c;
    }

    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return arrayList.get(i).sublist.size();
    }

    @Override
    public Object getGroup(int i) {
        return arrayList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return arrayList.get(i).sublist.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if(view == null){
            view = inflater.inflate(R.layout.sales_list_item,null);
        }
        TextView t1 = (TextView)view.findViewById(R.id.sales_list_item_name_tv);
        TextView t2 = (TextView)view.findViewById(R.id.sales_list_item_sale_tv);
        Sales one = (Sales)getGroup(i);
        t1.setText(one.date);
        t2.setText(one.sale+"");
        Log.d("BEOM9", "sub child : " + one.sublist.get(0).date);
        return view;        }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if(view == null){
            view = inflater.inflate(R.layout.sales_list_item,null);
        }
        TextView t1 = (TextView)view.findViewById(R.id.sales_list_item_name_tv);
        TextView t2 = (TextView)view.findViewById(R.id.sales_list_item_sale_tv);
        Sales one = (Sales)getChild(i,i1);
        Log.d("BEOM9", "child : "+ one.date);
        t1.setText(one.date);
        t2.setText(one.sale+"");
        return view;        }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
