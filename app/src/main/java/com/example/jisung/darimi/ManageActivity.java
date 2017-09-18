package com.example.jisung.darimi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;

//test
public class ManageActivity extends AppCompatActivity {
    // basis// basis
    Intent intent;
    String time;
    TextView time_N;
    //
    EditText custom_search_edt;
    ListView custom_list;
    static ArrayList<Custom> arrayList;// = new ArrayList<Custom>();
    CustomAdapter adapter;

    String init = "";
    Button all_, r_, s_, e_, f_, a_, q_, t_, d_, w_, c_, z_, x_, v_, g_;
    ArrayList<Button> select_btn_array = new ArrayList<Button>();

    private static Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Typekit.getInstance()

                .addNormal(Typekit.createFromAsset(this, "rix.ttf"))
                .addBold(Typekit.createFromAsset(this, "rixb.TTF"));
        init();
        adapter.filter("");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    //basis
    void init() {
        all_ = (Button) findViewById(R.id.all_search_btn);
        g_ = (Button) findViewById(R.id.g_search_btn);
        r_ = (Button) findViewById(R.id.r_search_btn);
        s_ = (Button) findViewById(R.id.s_search_btn);
        e_ = (Button) findViewById(R.id.e_search_btn);
        f_ = (Button) findViewById(R.id.f_search_btn);
        a_ = (Button) findViewById(R.id.a_search_btn);
        q_ = (Button) findViewById(R.id.q_search_btn);
        t_ = (Button) findViewById(R.id.t_search_btn);
        d_ = (Button) findViewById(R.id.d_search_btn);
        w_ = (Button) findViewById(R.id.w_search_btn);
        c_ = (Button) findViewById(R.id.c_search_btn);
        z_ = (Button) findViewById(R.id.z_search_btn);
        x_ = (Button) findViewById(R.id.x_search_btn);
        v_ = (Button) findViewById(R.id.v_search_btn);
        g_ = (Button) findViewById(R.id.g_search_btn);
        select_btn_array.add(all_);
        select_btn_array.add(r_);
        select_btn_array.add(s_);
        select_btn_array.add(e_);
        select_btn_array.add(f_);
        select_btn_array.add(a_);
        select_btn_array.add(q_);
        select_btn_array.add(t_);
        select_btn_array.add(d_);
        select_btn_array.add(w_);
        select_btn_array.add(c_);
        select_btn_array.add(z_);
        select_btn_array.add(x_);
        select_btn_array.add(v_);
        select_btn_array.add(g_);

        //basis
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        time_N.setText(time);
        //
        realm.init(this);
        realm = Realm.getDefaultInstance();

        arrayList = new ArrayList<Custom>(realm.where(Custom.class).findAll());
        Log.d("BEOMm", "arrayList.size() : " + arrayList.size());
        custom_search_edt = (EditText) findViewById(R.id.custom_search_edt);
        custom_list = (ListView) findViewById(R.id.custom_list);
        adapter = new CustomAdapter(arrayList, this, realm);
        custom_list.setAdapter(adapter);

        custom_search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String text = custom_search_edt.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
                if (text.length() != 0) {
                    for (int i = 0; i < select_btn_array.size(); i++) {
                        select_btn_array.get(i).setBackgroundResource(R.drawable.rounded_btn);
                    }
                }
            }
        });

    }

    private RealmResults<Custom> getUserList() {
        return realm.where(Custom.class).findAll();
    }

    public static void insertuserData(final String name, final String call) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Custom user = realm.createObject(Custom.class, call);
                user.setName(name);
//                user.setCall(call);
                user.setNum(arrayList.size() + 1 + "");
                realm.insert(user);
            }
        });
    }

    public static void UpdateCus(Custom c) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(c);
        realm.commitTransaction();
    }


    public List<Custom> getCustomList() {
        List<Custom> list = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Custom> results = realm
                    .where(Custom.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
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
                        /*
                        // 현재시간을 msec 으로 구한다.
                        long now = System.currentTimeMillis();
                        // 현재시간을 date 변수에 저장한다.
                        Date date = new Date(now);
                        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        // nowDate 변수에 값을 저장한다.
                        String id = sdfNow.format(date);
                        */
                        if (custom_name.length() != 0 && custom_call.length() != 0) {
                            if (adapter.isInitialSound(custom_name.charAt(0))) {
                                setCustomToast(ManageActivity.this, "이름을 제대로 입력해주세요.");
//                                Toast.makeText(ManageActivity.this, "이름을 제대로 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else if (custom_call.length() <= 9) {
setCustomToast(ManageActivity.this, "전화번호를 제대로 입력해주세요.");
//                                Toast.makeText(ManageActivity.this, "전화번호를 제대로 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else {
                                int flag = Check_call(custom_call);

                                if(flag==0) {
                                    Custom new_Custom = new Custom(custom_name, custom_call);
                                    insertuserData(custom_name, custom_call);
                                    adapter.searchList.add(new_Custom);
                                    adapter.notifyDataSetChanged();
                                    adapter.filter(custom_search_edt.getText().toString());
                                    //                                Initial_Search(init);
                                    adapter.filter(init);
                                    custom_search_edt.setText(custom_name);

                                    dialog.dismiss();
                                }else {
                                    setCustomToast(ManageActivity.this, "이미 존재하는 전화번호 입니다.");
//                                    Toast.makeText(getApplicationContext(), "이미 존재하는 전화번호 입니다.", Toast.LENGTH_LONG).show();

                                }
                            }
                        } else {
                            setCustomToast(ManageActivity.this, "모든 항목을 입력해주세요.");
//                            Toast.makeText(ManageActivity.this, "모든 항목을 입력해주세요.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            default:
                break;

        }
    }
    //
    //
    //    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    //    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    //    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    //    private static final char[] INITIAL_SOUND = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    //
    //    /**
    //     * 해당 문자의 자음을 얻는다.
    //     *
    //     * @param c 검사할 문자
    //     * @return
    //     */
    //    private static char getInitialSound(char c) {
    //        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
    //        int index = hanBegin / HANGUL_BASE_UNIT;
    //        return INITIAL_SOUND[index];
    //    }
    //
    //    void Initial_Search(char c) {
    //        custom_search_edt.setText("");
    //        arrayList.clear();
    //        for (int i = 0; i < adapter.searchList.size(); i++) {
    //            Log.d("BEOM5", "initial : " + adapter.searchList.get(i).name.charAt(0));
    ////                    arrayList.get(i).name.charAt(0)
    //            if (getInitialSound(adapter.searchList.get(i).name.charAt(0)) == c) {
    //                arrayList.add(adapter.searchList.get(i));
    //            }
    //        }
    //        for (int i = 0; i < arrayList.size(); i++) {
    //            arrayList.get(i).num = String.valueOf(i + 1);
    //        }
    //        adapter.notifyDataSetChanged();
    //        this.init = c;
    //
    //    }


    public void search_Click(View v) {
        switch (v.getId()) {
            case R.id.all_search_btn:
                adapter.filter("");
                select_btn(all_);
                break;
            case R.id.r_search_btn:
                //                Initial_Search('ㄱ');
                select_btn(r_);
                adapter.filter("ㄱ");

                break;
            case R.id.s_search_btn:
                select_btn(s_);
                //                Initial_Search('ㄴ');
                adapter.filter("ㄴ");

                break;
            case R.id.e_search_btn:
                select_btn(e_);
                //                Initial_Search('ㄷ');
                adapter.filter("ㄷ");

                break;
            case R.id.f_search_btn:
                select_btn(f_);
                //                Initial_Search('ㄹ');
                adapter.filter("ㄹ");

                break;
            case R.id.a_search_btn:
                select_btn(a_);
                //                Initial_Search('ㅁ');
                adapter.filter("ㅁ");

                break;
            case R.id.q_search_btn:
                select_btn(q_);
                //                Initial_Search('ㅂ');
                adapter.filter("ㅂ");

                break;
            case R.id.t_search_btn:
                select_btn(t_);
                //                Initial_Search('ㅅ');
                adapter.filter("ㅅ");

                break;
            case R.id.d_search_btn:
                select_btn(d_);
                //                Initial_Search('ㅇ');
                adapter.filter("ㅇ");

                break;
            case R.id.w_search_btn:
                select_btn(w_);
                //                Initial_Search('ㅈ');
                adapter.filter("ㅈ");

                break;
            case R.id.c_search_btn:
                select_btn(c_);
                //                Initial_Search('ㅊ');
                adapter.filter("ㅊ");

                break;
            case R.id.z_search_btn:
                select_btn(z_);
                //                Initial_Search('ㅋ');
                adapter.filter("ㅋ");

                break;
            case R.id.x_search_btn:
                select_btn(x_);
                //                Initial_Search('ㅌ');
                adapter.filter("ㅌ");

                break;
            case R.id.v_search_btn:
                select_btn(v_);
                //                Initial_Search('ㅍ');
                adapter.filter("ㅍ");

                break;
            case R.id.g_search_btn:
                select_btn(g_);
                //                Initial_Search('ㅎ');
                adapter.filter("ㅎ");

                break;
        }

    }

    void select_btn(Button selec) {
        custom_search_edt.setText("");
        Log.d("BEOM6", "Btn : " + selec.getText());
        selec.setBackgroundResource(R.drawable.selec_rounded_btn);
        select_btn_array.remove(selec);
        for (int i = 0; i < select_btn_array.size(); i++) {
            Log.d("BEOM6", "arrBtn : " + select_btn_array.get(i).getText());
            select_btn_array.get(i).setBackgroundResource(R.drawable.rounded_btn);
        }
        select_btn_array.clear();
        select_btn_array.add(all_);
        select_btn_array.add(r_);
        select_btn_array.add(s_);
        select_btn_array.add(e_);
        select_btn_array.add(f_);
        select_btn_array.add(a_);
        select_btn_array.add(q_);
        select_btn_array.add(t_);
        select_btn_array.add(d_);
        select_btn_array.add(w_);
        select_btn_array.add(c_);
        select_btn_array.add(z_);
        select_btn_array.add(x_);
        select_btn_array.add(v_);
        select_btn_array.add(g_);
    }

    public static void removeCus(final String call) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Custom> result = realm.where(Custom.class).equalTo("call", call).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public int Check_call(String call){
        ArrayList<Custom> arrayList = (ArrayList<Custom>) getCustomList();
        for(int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).getCall().equals(call)){
                Log.d("BEOM22", "arrayList.get(i).getCall() : " + arrayList.get(i).getCall());
                Log.d("BEOM22","custom_call : " + call);
                return 1;
            }
        }
        return 0;
    }
    public void setCustomToast(Context context, String msg) {

        TextView tvToastMsg = new TextView(context);
        tvToastMsg.setText(msg);
//        tvToastMsg.setBackgroundResource(Color.WHITE);
        tvToastMsg.setTextColor(getResources().getColor(R.color.Key));
        tvToastMsg.setTextSize(16);
        final Toast toastMsg = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toastMsg.setView(tvToastMsg);
        toastMsg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toastMsg.cancel();
            }
        }, 1000);
    }
}
