package com.example.jisung.darimi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jisung on 2017-09-09.
 */

public class work_nameAdapter extends BaseExpandableListAdapter {

    ArrayList<work_date_list> list;
    Context context;
    ArrayList<Order> Alls,Aworks,Bworks;
    work_itemAdapter Aadapter,Badapter;

    public work_nameAdapter(ArrayList<work_date_list> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getOrders().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return list.get(groupPosition).getOrders().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }


    public void listChange(Order order){
        for(int i=0;i<list.size();i++) {
            for (int j = 0; j < list.get(i).getOrders().size(); j++) {
                if (list.get(i).getOrders().get(j).equals(order)) {
                    list.get(i).getOrders().remove(j);
                    notifyDataSetChanged();
                    return;
                }

            }
        }
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.work_name_item, null);
        TextView date =(TextView)convertView.findViewById(R.id.date);
        date.setText(dateSet.b_date(list.get(groupPosition).getDate()));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.setting_list_item, null);
        TextView name =(TextView)convertView.findViewById(R.id.name);
        TextView item = (TextView)convertView.findViewById(R.id.item);
        Button button = (Button)convertView.findViewById(R.id.detailBtn);
        name.setText(list.get(groupPosition).getOrders().get(childPosition).getName());
        item.setText(itemParser.parserSumList(list.get(groupPosition).getOrders().get(childPosition).getData()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmp=0;
                for(int i=0;i<Bworks.size();i++){
                    if(Bworks.get(i).getName().equals(list.get(groupPosition).getOrders().get(childPosition).getName()))
                        Collections.swap(Bworks,tmp++,i);

                }
                tmp=0;
                for(int i=0;i<Aworks.size();i++){
                    if(Aworks.get(i).getName().equals(list.get(groupPosition).getOrders().get(childPosition).getName()))
                        Collections.swap(Aworks,tmp++,i);

                }
                Aadapter.notifyDataSetChanged();
                Badapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
