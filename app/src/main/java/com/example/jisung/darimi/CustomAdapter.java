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

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by parkbeommin on 2017. 8. 3..
 */

public class CustomAdapter extends BaseAdapter {
    //    darimiDB database;
    Realm realm;
    ArrayList<Custom> searchList; //원본 리스트
    ArrayList<Custom> arrayList; //검색된 결과가 들어있는 리스트
    Context c;
    String charText = "";

    public CustomAdapter(ArrayList<Custom> arrayList, Context c, Realm realm) {
//        this.database = database;
        this.realm = realm;
        this.arrayList = arrayList;
        this.c = c;
        searchList = new ArrayList<Custom>();
        if (arrayList.size() != 0) {
            searchList.addAll(arrayList);
        }
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
                ManageActivity m = new ManageActivity();
                m.removeCus(one.id);
                realm.beginTransaction();
                for (int i = 0; i < searchList.size(); i++) {
                    searchList.get(i).setNum(Integer.toString(i + 1)); //= Integer.toString(i + 1);
                }
                realm.cancelTransaction();
                CustomAdapter.this.notifyDataSetChanged();
                filter(charText);
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
//                        realm.beginTransaction();
                        String origin_name = one.name;
                        String origin_call = one.call;
                        String modify_name = modify_custom_name_edt.getText().toString();
                        String modify_call = modify_custom_call_edt.getText().toString();
                        ManageActivity m = new ManageActivity();
                        ManageActivity m1 = new ManageActivity();

                        if (modify_custom_name_edt.length() == 0 && modify_custom_call_edt.length() == 0) {
                            Toast.makeText(c, "변동사항이 없습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            searchList.remove(searchList.indexOf(arrayList.get(position)));
                            int num = getCount();//arrayList.size() + 1;
                            if (modify_custom_name_edt.length() == 0) {
                                Custom modify_custom = new Custom(one.id, Integer.toString(num), origin_name, modify_call);

//                                RealmResults<Custom> userList = realm.where(Custom.class).equalTo("id",one.id).findAll();
//                                userList.remove(0);
//                                Custom user = realm.createObject(Custom.class, one.id);
//                                user.setName(origin_name);
//                                user.setCall(modify_call);
//                                user.setNum(Integer.toString(num));
//                                realm.insert(user);
                                m.UpdateCus(modify_custom);
//                                m.removeCus(one.id);
//                                m1.insertuserData(one.id, origin_name, modify_call);
//                                database.Update_Custom(one.id, origin_name, modify_call);
//                                database.Get_Custom(arrayList);
//                                arrayList.add(modify_custom);
//                                for (int i = 0; i < arrayList.size(); i++) {
//                                    arrayList.get(i).num = Integer.toString(i + 1);
//                                }
                                searchList.add(modify_custom);
                                CustomAdapter.this.notifyDataSetChanged();
                            } else if (modify_custom_call_edt.length() == 0) {
                                Custom modify_custom = new Custom(one.id, Integer.toString(num), modify_name, origin_call);
//                                database.Update_Custom(one.id, modify_name, origin_call);
//                                database.Get_Custom(arrayList);
//                                arrayList.add(modify_custom);
//                                for (int i = 0; i < arrayList.size(); i++) {
//                                    arrayList.get(i).num = Integer.toString(i + 1);
//                                }
m.UpdateCus(modify_custom);
//                                m.removeCus(one.id);
//                                m1.insertuserData(one.id, modify_name, origin_call);
                                searchList.add(modify_custom);

                                CustomAdapter.this.notifyDataSetChanged();
                            } else {
                                Custom modify_custom = new Custom(one.id, Integer.toString(num), modify_name, modify_call);
//                                database.Update_Custom(one.id, modify_name, modify_call);
//                                database.Get_Custom(arrayList);
//                                arrayList.add(modify_custom);
//                                for (int i = 0; i < arrayList.size(); i++) {
//                                    arrayList.get(i).num = Integer.toString(i + 1);
//                                }
//                                RealmResults<Custom> userList = realm.where(Custom.class).equalTo("id",one.id).findAll();
//                                userList.remove(0);
//                                Custom user = realm.createObject(Custom.class, one.id);
//                                user.setName(modify_name);
//                                user.setCall(modify_call);
//                                user.setNum(Integer.toString(num));
//                                realm.insert(user);
//                                m.removeCus(one.id);
//                                m1.insertuserData(one.id, modify_name, modify_call);
                                m.UpdateCus(modify_custom);
                                searchList.add(modify_custom);

                                CustomAdapter.this.notifyDataSetChanged();
                            }
                        }
                        realm.beginTransaction();
                        for (int i = 0; i < searchList.size(); i++) {
                            searchList.get(i).setNum(Integer.toString(i + 1));// = Integer.toString(i + 1);
                        }
                        realm.cancelTransaction();

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
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(searchList);
        } else {
            if (isInitialSound(charText.charAt(0))) {
                Log.d("BEOM", "initial : " + charText);
                Initial_Search(charText.charAt(0));
            } else {
                Log.d("BEOM", "not initial : " + charText);
                charText = charText.toLowerCase(Locale.getDefault());
                for (Custom custom : searchList) {
                    String name = custom.name;
                    if (name.toLowerCase().contains(charText)) {
                        arrayList.add(custom);
                    }
                }
            }
        }
        realm.beginTransaction();
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d("BEOMm1", "num sort");
            arrayList.get(i).setNum(String.valueOf(i + 1)); //= String.valueOf(i + 1);
            Log.d("BEOMm1", "arrayList.get(i).getNum() : " + arrayList.get(i).getNum());
            Log.d("BEOMm1", "searchList.get(i).getNum() : " + searchList.get(i).getNum());
        }        realm.commitTransaction();

//        realm.cancelTransaction();
//        realm.commitTransaction();
        this.notifyDataSetChanged();
    }

    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    private static final char[] INITIAL_SOUND = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    /**
     * 해당 문자의 자음을 얻는다.
     *
     * @param c 검사할 문자
     * @return
     */
    private static char getInitialSound(char c) {
        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;
        return INITIAL_SOUND[index];
    }

    void Initial_Search(char c) {
//        realm.beginTransaction();

//        custom_search_edt.setText("");
        arrayList.clear();
        for (int i = 0; i < searchList.size(); i++) {
            Log.d("BEOM5", "initial : " + searchList.get(i).name.charAt(0));
//                    arrayList.get(i).name.charAt(0)
            if (getInitialSound(searchList.get(i).name.charAt(0)) == c) {
                arrayList.add(searchList.get(i));
            }
        }
        realm.beginTransaction();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setNum(String.valueOf(i + 1)); //= String.valueOf(i + 1);
        }
        realm.cancelTransaction();
        notifyDataSetChanged();
        ManageActivity manageActivity = new ManageActivity();
        manageActivity.init = c + "";
//        this.init = c;
    }

    /**
     * 해당 문자가 INITIAL_SOUND인지 검사.
     *
     * @param searchar
     * @return
     */
    public static boolean isInitialSound(char searchar) {
        for (char c : INITIAL_SOUND) {
            if (c == searchar) {
                return true;
            }
        }
        return false;
    }
}
