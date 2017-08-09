package com.example.jisung.darimi;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by parkbeommin on 2017. 8. 3..
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<Custom> searchList;
    ArrayList<Custom> arrayList;
    Context c;
String charText;
    public CustomAdapter(ArrayList<Custom> arrayList, Context c) {
        this.arrayList = arrayList;
        this.c = c;
        searchList = new ArrayList<Custom>();
        searchList.addAll(arrayList);
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
        final LayoutInflater inflater = LayoutInflater.from(c);
        if (view == null) {
            view = inflater.inflate(R.layout.custom_list, null);
        }
        TextView num = (TextView) view.findViewById(R.id.custom_list_num_tv);
        TextView name = (TextView) view.findViewById(R.id.custom_list_name_tv);
        TextView call = (TextView) view.findViewById(R.id.custom_list_call_tv);
        Button custom_list_modify_btn = (Button) view.findViewById(R.id.custom_list_modify_btn);
        Button custom_list_delete_btn = (Button) view.findViewById(R.id.custom_list_delete_btn);

        final Custom one;
        one = arrayList.get(i);
        final int position = i;

        num.setText(one.num);
        name.setText(one.name);
        String call_text = "";
        if (one.call.length() == 11) {
            call_text = one.call.substring(0, 3) + "-" + one.call.substring(3, 7) + "-" + one.call.substring(7);
        } else {
            call_text = one.call.substring(0, 3) + "-" + one.call.substring(3, 6) + "-" + one.call.substring(6);
        }
        call.setText(call_text);
        custom_list_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchList.remove(searchList.indexOf(arrayList.get(position)));
                arrayList.remove(position);

                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).num = Integer.toString(i + 1);
                }
                for (int i = 0; i < searchList.size(); i++) {
                    searchList.get(i).num = Integer.toString(i + 1);
                }
                CustomAdapter.this.notifyDataSetChanged();
//                searchList.clear();
//                searchList.addAll(arrayList);
                Toast.makeText(c, "삭제되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
        custom_list_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View modify_custom;
                modify_custom = inflater.inflate(R.layout.add_custom, null);
                final TextView modify_custom_title = (TextView) modify_custom.findViewById(R.id.add_custom_title_tv);
                final EditText modify_custom_name_edt = (EditText) modify_custom.findViewById(R.id.add_custom_name_edt);
                final EditText modify_custom_call_edt = (EditText) modify_custom.findViewById(R.id.add_custom_call_edt);
                final ImageButton modify_custom_cancel_btn = (ImageButton) modify_custom.findViewById(R.id.add_custom_close_btn);
                final Button modify_custom_confirm_btn = (Button) modify_custom.findViewById(R.id.add_custom_confirm_btn);
                modify_custom_title.setText("고객정보수정");
                final AlertDialog dialog = new AlertDialog.Builder(c).create();
                dialog.setView(modify_custom);
                dialog.show();
                modify_custom_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                modify_custom_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String origin_name = one.name;
                        String origin_call = one.call;
                        String modify_name = modify_custom_name_edt.getText().toString();
                        String modify_call = modify_custom_call_edt.getText().toString();

                        if (modify_custom_name_edt.length() == 0 && modify_custom_call_edt.length() == 0) {
                            Toast.makeText(c, "변동사항이 없습니다.", Toast.LENGTH_LONG).show();
                        } else {
//                            arrayList.remove(position);
                            searchList.remove(searchList.indexOf(arrayList.get(position)));
                            arrayList.remove(position);
//                            searchList.remove(position);
                            int num = getCount();//arrayList.size() + 1;
                            if (modify_custom_name_edt.length() == 0) {
                                Custom modify_custom = new Custom(Integer.toString(num), origin_name, modify_call);
                                arrayList.add(modify_custom);
                                for (int i = 0; i < arrayList.size(); i++) {
                                    arrayList.get(i).num = Integer.toString(i + 1);
                                }
                                searchList.add(modify_custom);
                                CustomAdapter.this.notifyDataSetChanged();
                            } else if (modify_custom_call_edt.length() == 0) {
                                Custom modify_custom = new Custom(Integer.toString(num), modify_name, origin_call);
                                arrayList.add(modify_custom);
                                for (int i = 0; i < arrayList.size(); i++) {
                                    arrayList.get(i).num = Integer.toString(i + 1);
                                }
                                searchList.add(modify_custom);

                                CustomAdapter.this.notifyDataSetChanged();
                            } else {
                                Custom modify_custom = new Custom(Integer.toString(num), modify_name, modify_call);
                                arrayList.add(modify_custom);
                                for (int i = 0; i < arrayList.size(); i++) {
                                    arrayList.get(i).num = Integer.toString(i + 1);
                                }
                                searchList.add(modify_custom);

                                CustomAdapter.this.notifyDataSetChanged();
                            }
                        }for (int i = 0; i < searchList.size(); i++) {
                            searchList.get(i).num = Integer.toString(i + 1);
                        }
//                        searchList.clear();
//                        searchList.addAll(arrayList);
                        filter(charText);
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    public void filter(String charText) {
        this.charText = charText;
        charText = charText.toLowerCase(Locale.getDefault());
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(searchList);

        } else {
            for (Custom custom : searchList) {
                String name = custom.name;
                if (name.toLowerCase().contains(charText)) {
                    arrayList.add(custom);
                }
            }
        }for (int i = 0; i < arrayList.size(); i++){
            arrayList.get(i).num = String.valueOf(i+1);
        }
        notifyDataSetChanged();
    }


}
