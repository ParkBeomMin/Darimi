package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class ItemAdapter extends DragItemAdapter<Item, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    Realm realm;
    int cate;
    OrderActivity O = new OrderActivity();

    ItemAdapter(ArrayList<Item> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
//        if(mItemList.get)
        String text = mItemList.get(position).getName();
        holder.name.setText(text);
        holder.price.setText(mItemList.get(position).getPrice());
        holder.img.setImageResource(mItemList.get(position).getImg());
        holder.itemView.setTag(mItemList.get(position));
        if(mItemList.get(position).isMark())
            holder.mark.setImageResource(R.drawable.item_marked);
    }


    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getSeq();
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView name,price;
        ImageView img,mark;
        Spinner spinner;




        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            name =(TextView)itemView.findViewById(R.id.item_name);
            price = (TextView)itemView.findViewById(R.id.item_price);
            img = (ImageView)itemView.findViewById(R.id.item_img);
            mark = (ImageView)itemView.findViewById(R.id.item_mark);

        }

        @Override
        public void onItemClicked(View view) {
            Log.d("testSeq", getItemId() + "");
        }

        @Override
        public boolean onItemLongClicked(View view) {
            O.setCustomToast(view.getContext(), "Item long clicked");
//            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
