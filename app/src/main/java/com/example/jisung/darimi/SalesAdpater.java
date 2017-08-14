package com.example.jisung.darimi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class SalesAdpater extends BaseExpandableListAdapter {
ArrayList<Sales> arrayList;
    Context c;

    public SalesAdpater(ArrayList<Sales> arrayList, Context c) {
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
        return view;    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if(view == null){
            view = inflater.inflate(R.layout.sublist,null);
        }
        ExpandableListView listView = (ExpandableListView)view.findViewById(R.id.sales_sublist);
        ArrayList<Sales> sub = new ArrayList<Sales>();
//        sub.addAll(arrayList.get(i).sublist);
//        Sales s1 = (Sales)getChild(i,i1);
        Sales s1 = new Sales("sub1",1);
//        sub.add(s1);
        s1.sublist.add(new Sales("subsub",1));
        sub.add(s1);
//        Log.d("BEOM9", "sub_size : " + sub.size());
//        Log.d("BEOM9", "sub_0 : " + sub.get(0).date);
//        Log.d("BEOM9", "subsub_0 : " + sub.get(0).sublist.get(0).date);
//        SalesSubAdapter adapter = new SalesSubAdapter(sub,c);
//        listView.setAdapter(adapter);
//        TextView t1 = (TextView)view.findViewById(R.id.sales_list_item_name_tv);
//        TextView t2 = (TextView)view.findViewById(R.id.sales_list_item_sale_tv);
//        Sales one = (Sales)getChild(i,i1);
//        t1.setText(one.date);
//        t2.setText(one.sale+"");
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(c, "hi",Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
