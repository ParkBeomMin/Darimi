package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by jisung on 2017-09-17.
 */

public class ItemAddAdapter extends BaseAdapter {
    ArrayList<Item> list;
    private Context context;
    Realm realm;
    int cate=0;

    public ItemAddAdapter(ArrayList<Item> list, Context context, Realm realm) {
        this.list = list;
        this.context = context;
        this.realm = realm;
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
    public View getView(int position, View view, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null)
            view = inflater.inflate(R.layout.add_item_layout, null);

        return view;
    }
}
