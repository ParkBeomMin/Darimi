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

public class SalesAdpater extends BaseExpandableListAdapter {
    private ArrayList<Sales> groupList = null;
    private ArrayList<ArrayList<Sales>> childList = null;
    private Context c;

    public SalesAdpater(ArrayList<Sales> groupList, ArrayList<ArrayList<Sales>> childList, Context c) {
        this.groupList = groupList;
        this.childList = childList;
        this.c = c;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if (view == null) {
            view = inflater.inflate(R.layout.sales_list, null);
        }
        TextView date = (TextView) view.findViewById(R.id.sales_list_date_tv);
        TextView sale = (TextView) view.findViewById(R.id.sales_list_sale_tv);
        Sales one;
        one = groupList.get(i);
        date.setText(one.date);
        sale.setText("" + one.sale);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if (view == null) {
            view = inflater.inflate(R.layout.sublist, null);
        }
        TextView date = (TextView) view.findViewById(R.id.sublist_name_tv);
        TextView sale = (TextView) view.findViewById(R.id.sublist_sale_tv);
        Sales one;
        one = childList.get(i).get(i1);
        date.setText(one.date);
        sale.setText("" + one.sale);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
