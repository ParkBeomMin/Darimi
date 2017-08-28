package com.example.jisung.darimi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;

/**
 * Created by parkbeommin on 2017. 8. 8..
 */

public class SalesAdpater extends TreeNode.BaseNodeViewHolder<SalesAdpater.TreeItem> {
//ArrayList<Sales> arrayList;
//    Context c;
    public SalesAdpater(Context context) {
        super(context);
//        this.c = c;
    }

    @Override
    public View createNodeView(final TreeNode node, TreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sales_list_item, null);
        TextView t1 = (TextView)view.findViewById(R.id.sales_list_item_name_tv);
        TextView t2 = (TextView)view.findViewById(R.id.sales_list_item_sale_tv);
        TextView v1 = (TextView)view.findViewById(R.id.beom);
        t1.setText(value.s1);
        t2.setText(value.s2);

        if(node.getLevel()==1){
            v1.setPadding(550,0,0,0);
        }
        if(node.getLevel()==2){
            t1.setPadding(50,0,0,0);
            v1.setPadding(500,0,0,0);
        }
        if(node.getLevel()==3){
            t1.setPadding(100,0,0,0);
            v1.setPadding(450,0,0,0);
        }
        if(node.getLevel()==4){
            t1.setPadding(150,0,0,0);
            v1.setPadding(400,0,0,0);
        }
//
//        View set_term = inflater.inflate(R.layout.activity_sales, null);
//        ImageButton start_left = (ImageButton)set_term.findViewById(R.id.sales_start_left_btn);
//        start_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getTreeView().removeNode(node);
//                getTreeView().addNode(node, new TreeNode(new SalesAdpater.TreeItem( "일", "400,000원")).setViewHolder(new SalesAdpater(context)));
//            }
//        });
        return view;

    }


    public static class TreeItem{
        public String s1;
        public String s2;

        public String getS1() {
            return s1;
        }

        public String getS2() {
            return s2;
        }

        public TreeItem(String s1, String s2){
            this.s1 = s1;
            this.s2 = s2;

        }
    }

}
