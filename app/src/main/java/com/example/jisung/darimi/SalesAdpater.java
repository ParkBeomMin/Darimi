package com.example.jisung.darimi;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
        TextView t1 = (TextView) view.findViewById(R.id.sales_list_item_name_tv);
        TextView t2 = (TextView) view.findViewById(R.id.sales_list_item_sale_tv);
        TextView v1 = (TextView) view.findViewById(R.id.beom);
        ImageButton i1 = (ImageButton)view.findViewById(R.id.expand_btn);
        RelativeLayout l1 = (RelativeLayout) view.findViewById(R.id.sales_list_item_lin);
        LinearLayout l2 = (LinearLayout) view.findViewById(R.id.Linear);
        View view1 = (View) view.findViewById(R.id.line);

//        RelativeLayout.LayoutParams plControl = (RelativeLayout.LayoutParams) l1.getLayoutParams();

        t1.setText(value.s1);
        t2.setText(value.s2);
        if (value.s1.length() == 0) {
//            t1.setTextSize(0);
//            t2.setTextSize(0);
//            v1.setTextSize(0);
//            v1.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
        } else {
            l1.setVisibility(View.VISIBLE);
        }

        if (node.getLevel() == 1) {
//            v1.setPadding(550,0,0,0);
            l1.setBackgroundResource(R.color.Sales_list_root);
            l2.setBackgroundResource(R.color.Sales_list_root);
            t1.setBackgroundResource(R.color.Sales_list_root);
            t2.setBackgroundResource(R.color.Sales_list_root);
            v1.setBackgroundResource(R.color.Sales_list_root);
            t1.setPadding(0, 30, 0, 30);
            t2.setPadding(0, 30, 0, 30);
            i1.setPadding(0, 30, 0, 30);
            t2.setText(value.s2);
            t2.setTextSize(18);
            t1.setTextSize(18);
            v1.setVisibility(View.VISIBLE);i1.setVisibility(View.VISIBLE);
            v1.setText("(단위 : 원)");
            t1.setTextColor(Color.parseColor("#636870"));
            t2.setTextColor(Color.parseColor("#636870"));
            v1.setTextColor(Color.parseColor("#636870"));
            view1.setVisibility(View.VISIBLE);
        }
        if (node.getLevel() == 2) {
            t1.setPadding(50, 20, 0, 20);
            t2.setPadding(0, 20, 0, 20);
            t1.setTextColor(Color.parseColor("#636870"));
            t2.setTextColor(Color.parseColor("#636870"));
        }
        if (node.getLevel() == 3) {
            t1.setPadding(100, 0, 0, 0);

            t1.setTextColor(Color.parseColor("#8e8e8e"));
            t2.setTextColor(Color.parseColor("#8e8e8e"));
        }
        if (node.getLevel() == 4) {
            t1.setTextColor(Color.parseColor("#a5a5a5"));
            t2.setTextColor(Color.parseColor("#a5a5a5"));
            t1.setPadding(150, 0, 0, 0);
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


    public static class TreeItem {
        public String s1;
        public String s2;

        public String getS1() {
            return s1;
        }

        public String getS2() {
            return s2;
        }

        public TreeItem(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;

        }
    }

}
