package com.example.jisung.darimi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;
//test
public class ManageActivity extends AppCompatActivity {
    // basis
    Intent intent;
    String time;
    TextView time_N;
    //

    EditText custom_search_edt;
    ListView custom_list;
    ArrayList<Custom> arrayList = new ArrayList<Custom>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        init();
    }

    //basis
    void init() {

        //basis
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        time_N.setText(time);
        //

        custom_search_edt = (EditText) findViewById(R.id.custom_search_edt);
        custom_list = (ListView) findViewById(R.id.custom_list);
        //test
        arrayList.add(new Custom("1","박범민","01024347280"));
        arrayList.add(new Custom("2","정지성","01024347280"));
        arrayList.add(new Custom("3","문소연","01024347280"));
        arrayList.add(new Custom("4","남궁선","01024347280"));
        arrayList.add(new Custom("5","서수민","01024347280"));
        //
        adapter = new CustomAdapter(arrayList, this);
        custom_list.setAdapter(adapter);

        custom_search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = custom_search_edt.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            //basis
            case R.id.orderA:
                intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this, SalesActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;
            case R.id.settingA:
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;
            //
            case R.id.add_custom_btn:
                LayoutInflater inflater = getLayoutInflater();
                final View add_custom = inflater.inflate(R.layout.add_custom, null);
                final EditText add_custom_name_edt = (EditText) add_custom.findViewById(R.id.add_custom_name_edt);
                final EditText add_custom_call_edt = (EditText) add_custom.findViewById(R.id.add_custom_call_edt);
                final ImageButton add_custom_cancel_btn = (ImageButton) add_custom.findViewById(R.id.add_custom_close_btn);
                final Button add_custom_confirm_btn = (Button) add_custom.findViewById(R.id.add_custom_confirm_btn);
                final AlertDialog dialog = new AlertDialog.Builder(ManageActivity.this).create();
                dialog.setView(add_custom);
                dialog.show();
                add_custom_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                add_custom_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String custom_name = add_custom_name_edt.getText().toString();
                        String custom_call = add_custom_call_edt.getText().toString();
                        int num = arrayList.size() + 1;
                        if (custom_name.length() != 0 && custom_call.length() != 0) {
                            if (custom_call.length() > 9) {
                                Custom new_Custom = new Custom(Integer.toString(num), custom_name, custom_call);
                                arrayList.add(new_Custom);
//                                adapter.searchList.clear();
                                adapter.searchList.add(new_Custom);
                                adapter.notifyDataSetChanged();
                                adapter.filter(custom_search_edt.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ManageActivity.this, "전화번호를 제대로 입력해주세요.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageActivity.this, "모든 항목을 입력해주세요.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            default:
                break;

        }
    }
    //

    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

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
    void Initial_Search(char c){
        arrayList.clear();
        for(int i = 0; i < adapter.searchList.size(); i++){
            Log.d("BEOM5", "initial : " + adapter.searchList.get(i).name.charAt(0));
//                    arrayList.get(i).name.charAt(0)
            if(getInitialSound(adapter.searchList.get(i).name.charAt(0)) == c){
                arrayList.add(adapter.searchList.get(i));
            }
        }
        for(int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).num = String.valueOf(i+1);
        }
        adapter.notifyDataSetChanged();
    }
    public void search_Click(View v) {
        switch (v.getId()) {
            case R.id.all_search_btn:
                adapter.filter("");
                break;
            case R.id.r_search_btn:
                Initial_Search('ㄱ');
//                adapter.filter("ㄱ");
                break;
            case R.id.s_search_btn:
                Initial_Search('ㄴ');

                break;
            case R.id.e_search_btn:
                Initial_Search('ㄷ');

                break;
            case R.id.f_search_btn:
                Initial_Search('ㄹ');

                break;
            case R.id.a_search_btn:
                Initial_Search('ㅁ');

                break;
            case R.id.q_search_btn:
                Initial_Search('ㅂ');

                break;
            case R.id.t_search_btn:
                Initial_Search('ㅅ');

                break;
            case R.id.d_search_btn:
                Initial_Search('ㅇ');

                break;
            case R.id.w_search_btn:
                Initial_Search('ㅈ');

                break;
            case R.id.c_search_btn:
                Initial_Search('ㅊ');

                break;
            case R.id.z_search_btn:
                Initial_Search('ㅋ');

                break;
            case R.id.x_search_btn:
                Initial_Search('ㅌ');

                break;
            case R.id.v_search_btn:
                Initial_Search('ㅍ');

                break;
            case R.id.g_search_btn:
                Initial_Search('ㅎ');

                break;
        }

    }

}
