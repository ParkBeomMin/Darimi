package com.example.jisung.darimi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesActivity extends AppCompatActivity {
    static Realm realm;
    Button b1, b2, b3;

    Intent intent;
    TextView time_N;
    String time;

    TextView start_tv, finish_tv;
    TextView year, month;
    GridView calendar_gridview;
    ArrayList<String> day_list = new ArrayList<String>();
    CalendarAdapter adapter;
    Calendar my_calendar;

    TreeNode root;
    AndroidTreeView tView;
    ViewGroup containerView;
    TreeNode defalt_node;
    ArrayList<TreeNode> node_list = new ArrayList<TreeNode>();

    ArrayList<Sales> sales_list;//= new ArrayList<Sales>();

    int FILTER = 0;

    int CATEGORIZATION = 0; // 0 : all, 1 : card, 2 : cash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
        open_calendar();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    void init() {
        realm.init(this);
        realm = Realm.getDefaultInstance();
//        insertData("201709151113", "박범민", 26000, R.integer.BCard);
//        insertData("201709151114", "남궁선", 27000, R.integer.ACash);
//        insertData("201709201115", "문소연", 28000, R.integer.BCash);
//        insertData("201710091111", "박범민", 20000, R.integer.ACard);
//        insertData("201710091112", "정지성", 25000, R.integer.BCash);
//        insertData("201710111113", "박범민", 26000, true);
//        insertData("201711121114", "남궁선", 27000, true);
//        insertData("201712201115", "문소연", 28000, true);
//        insertData("201810111113", "박범민", 26000, true);
//        insertData("201811121114", "남궁선", 27000, true);
//        insertData("201812201115", "문소연", 28000, true);
        sales_list = (ArrayList<Sales>) getAllSalesList(CATEGORIZATION);

        b1 = (Button) findViewById(R.id.sales_list_day_btn);
        b2 = (Button) findViewById(R.id.sales_list_month_btn);
        b3 = (Button) findViewById(R.id.sales_list_year_btn);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "rix.ttf"))
                .addBold(Typekit.createFromAsset(this, "rixb.TTF"));
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        time_N.setText(time);

        start_tv = (TextView) findViewById(R.id.sales_start_tv);
        finish_tv = (TextView) findViewById(R.id.sales_finish_tv);
        year = (TextView) findViewById(R.id.sales_year_tv);
        month = (TextView) findViewById(R.id.sales_month_tv);
        calendar_gridview = (GridView) findViewById(R.id.sales_calendar);
        calendar_gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) // 그리드뷰 스크롤 막기
                {
                    return true;
                }
                return false;
            }
        });
        adapter = new CalendarAdapter(day_list, this);


        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        start_tv.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date) + "." + curDayFormat.format(date));
        finish_tv.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date) + "." + curDayFormat.format(date));

        year.setText(curYearFormat.format(date));
        month.setText(curMonthFormat.format(date));

        my_calendar = Calendar.getInstance();
        my_calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        setCalendarDate(my_calendar.get(Calendar.YEAR), my_calendar.get(Calendar.MONTH) + 1);
        adapter = new CalendarAdapter(day_list, getApplicationContext());
        calendar_gridview.setAdapter(adapter);
        adapter.getTitleYear(year.getText().toString());
        adapter.getTitleMonth(month.getText().toString());
        adapter.getStartYear(start_tv.getText().toString().substring(0, 4));
        adapter.getStartMonth(start_tv.getText().toString().substring(5, 7));
        adapter.getStartDay(start_tv.getText().toString().substring(8));
        adapter.getFinishYear(finish_tv.getText().toString().substring(0, 4));
        adapter.getFinishMonth(finish_tv.getText().toString().substring(5, 7));
        adapter.getFinishDay(finish_tv.getText().toString().substring(8));
        adapter.notifyDataSetChanged();


        root = TreeNode.root();
        tView = new AndroidTreeView(this, root);
        containerView = (ViewGroup) findViewById(R.id.sales_list);
        containerView.addView(tView.getView());

        start_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!start_tv.getText().toString().substring(0, 4).equals(finish_tv.getText().toString().substring(0, 4))) {
                    FILTER = 2;
                    set_enabled_btn(b3, b2, b1);
                } else if (!start_tv.getText().toString().substring(5, 7).equals(finish_tv.getText().toString().substring(5, 7))) {
                    FILTER = 1;
                    set_enabled_btn(b2, b1, b3);
                }
                set_list(start_tv, finish_tv, FILTER);
            }
        });

        finish_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!start_tv.getText().toString().substring(0, 4).equals(finish_tv.getText().toString().substring(0, 4))) {
                    FILTER = 2;
                    set_enabled_btn(b3, b2, b1);
                } else if (!start_tv.getText().toString().substring(5, 7).equals(finish_tv.getText().toString().substring(5, 7))) {
                    FILTER = 1;
                    set_enabled_btn(b2, b1, b3);
                }
                set_list(start_tv, finish_tv, FILTER);
            }
        });
        set_list(start_tv, finish_tv, 0);

    }

    private void setCalendarDate(int year, int month) {
        day_list.add("일");
        day_list.add("월");
        day_list.add("화");
        day_list.add("수");
        day_list.add("목");
        day_list.add("금");
        day_list.add("토");
        my_calendar.set(year, month - 1, 1);
        int dayNum = my_calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i++) {
            day_list.add("");
        }
        my_calendar.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            day_list.add("" + (i + 1));
        }
    }

    void open_calendar() {
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SalesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener,
                        Integer.parseInt(start_tv.getText().toString().substring(0, 4)), Integer.parseInt(start_tv.getText().toString().substring(5, 7)) - 1, Integer.parseInt(start_tv.getText().toString().substring(8)));
                datePickerDialog.show();
            }
        });
        finish_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SalesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener2,
                        Integer.parseInt(finish_tv.getText().toString().substring(0, 4)), Integer.parseInt(finish_tv.getText().toString().substring(5, 7)) - 1, Integer.parseInt(finish_tv.getText().toString().substring(8)));
                datePickerDialog.show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            if (i > Integer.parseInt(finish_tv.getText().toString().substring(0, 4)) || i1 + 1 > Integer.parseInt(finish_tv.getText().toString().substring(5, 7))) {

                Toast.makeText(SalesActivity.this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
            } else if (i == Integer.parseInt(finish_tv.getText().toString().substring(0, 4)) && i1 + 1 == Integer.parseInt(finish_tv.getText().toString().substring(5, 7))
                    && i2 > Integer.parseInt(finish_tv.getText().toString().substring(8))) {

                Toast.makeText(SalesActivity.this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
            } else {
                if (i1 + 1 < 10 && i2 < 10) {
                    start_tv.setText(i + ".0" + (i1 + 1) + ".0" + i2);

                } else if (i1 + 1 < 10) {
                    start_tv.setText(i + ".0" + (i1 + 1) + "." + i2);

                } else if (i2 < 10) {
                    start_tv.setText(i + "." + (i1 + 1) + ".0" + i2);

                } else {
                    start_tv.setText(i + "." + (i1 + 1) + "." + i2);
                }
            }
            adapter.getStartYear(String.valueOf(i));
            adapter.getStartMonth(String.valueOf(i1 + 1));
            adapter.getStartDay(String.valueOf(i2));
            adapter.notifyDataSetChanged();
        }
    };
    DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            if ((i < Integer.parseInt(start_tv.getText().toString().substring(0, 4))) || (i1 + 1 < Integer.parseInt(start_tv.getText().toString().substring(5, 7)))) {
                Log.d("BEOM14", "i : " + i);
                Log.d("BEOM14", "i1 : " + i1);
                Toast.makeText(SalesActivity.this, "끝나는 날짜가 시작 날짜보다 작을 수는 없습니다1", Toast.LENGTH_LONG).show();
            } else if ((i == Integer.parseInt(start_tv.getText().toString().substring(0, 4))) && (i1 + 1 == Integer.parseInt(start_tv.getText().toString().substring(5, 7)))
                    && (i2 < Integer.parseInt(start_tv.getText().toString().substring(8)))) {

                Toast.makeText(SalesActivity.this, "끝나는 날짜가 시작 날짜보다 작을 수는 없습니다2", Toast.LENGTH_LONG).show();
            } else {
                if (i1 + 1 < 10 && i2 < 10) {
                    finish_tv.setText(i + ".0" + (i1 + 1) + ".0" + i2);

                } else if (i1 + 1 < 10) {
                    finish_tv.setText(i + ".0" + (i1 + 1) + "." + i2);

                } else if (i2 < 10) {
                    finish_tv.setText(i + "." + (i1 + 1) + ".0" + i2);

                } else {
                    finish_tv.setText(i + "." + (i1 + 1) + "." + i2);
                }
            }
            adapter.getFinishYear(String.valueOf(i));
            adapter.getFinishMonth(String.valueOf(i1 + 1));
            adapter.getFinishDay(String.valueOf(i2));
            adapter.notifyDataSetChanged();
        }
    };

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.manageA:
                intent = new Intent(this, ManageActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;
            case R.id.orderA:
                intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                break;

            case R.id.settingA:
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;

            case R.id.sales_start_left_btn:
                String start_day = start_tv.getText().toString();
                String start_year = start_day.substring(0, 4);
                String start_month = start_day.substring(5, 7);
                String start_day_of_month = start_day.substring(8);
                set_calendar_term(start_year, start_month, start_day_of_month, start_tv, 0, 1);
                break;
            case R.id.sales_start_right_btn:
                String start_day_ = start_tv.getText().toString();
                String start_year_ = start_day_.substring(0, 4);
                String start_month_ = start_day_.substring(5, 7);
                String start_day_of_month_ = start_day_.substring(8);
                if (start_day_.equals(finish_tv.getText().toString())) {
                    Toast.makeText(this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
                } else {
                    set_calendar_term(start_year_, start_month_, start_day_of_month_, start_tv, 1, 1);
                }
                break;

            case R.id.sales_finish_left_btn:
                String finish_day = finish_tv.getText().toString();
                String finish_year = finish_day.substring(0, 4);
                String finish_month = finish_day.substring(5, 7);
                String finish_day_of_month = finish_day.substring(8);
                if (finish_day.equals(start_tv.getText().toString())) {
                    Toast.makeText(this, "끝나는 날짜가 시작 날짜보다 작을 수 없습니다.", Toast.LENGTH_LONG).show();
                } else {
                    set_calendar_term(finish_year, finish_month, finish_day_of_month, finish_tv, 0, 2);
                }
                break;
            case R.id.sales_finish_right_btn:
                String finish_day_ = finish_tv.getText().toString();
                String finish_year_ = finish_day_.substring(0, 4);
                String finish_month_ = finish_day_.substring(5, 7);
                String finish_day_of_month_ = finish_day_.substring(8);
                set_calendar_term(finish_year_, finish_month_, finish_day_of_month_, finish_tv, 1, 2);
                break;

            case R.id.sales_calendar_left_btn:
                day_list.clear();
                int prev_month = Integer.parseInt(month.getText().toString()) - 1;
                int prev_year = Integer.parseInt(year.getText().toString());
                if (prev_month < 1) {
                    prev_year -= 1;
                    year.setText(String.valueOf(prev_year));
                    prev_month = 12;
                }
                setCalendarDate(prev_year, prev_month);
                month.setText(String.valueOf(prev_month));
                adapter.getTitleYear(year.getText().toString());
                adapter.getTitleMonth(month.getText().toString());
                adapter.notifyDataSetChanged();
                break;
            case R.id.sales_calendar_right_btn:
                day_list.clear();
                int next_month = Integer.parseInt(month.getText().toString()) + 1;
                int next_year = Integer.parseInt(year.getText().toString());
                if (next_month > 12) {
                    next_year += 1;
                    year.setText(String.valueOf(next_year));
                    next_month = 1;
                }
                setCalendarDate(next_year, next_month);
                month.setText(String.valueOf(next_month));
                adapter.getTitleYear(year.getText().toString());
                adapter.getTitleMonth(month.getText().toString());
                adapter.notifyDataSetChanged();
                break;
            case R.id.sales_all_tv:
                CATEGORIZATION = 0;
                sales_list = (ArrayList<Sales>) getAllSalesList(CATEGORIZATION);

                set_list(start_tv, finish_tv, FILTER);
                break;
            case R.id.sales_card_tv:
                CATEGORIZATION = 1;                sales_list = (ArrayList<Sales>) getAllSalesList(CATEGORIZATION);
                set_list(start_tv, finish_tv, FILTER);

                break;
            case R.id.sales_cash_tv:
                CATEGORIZATION = 2;                sales_list = (ArrayList<Sales>) getAllSalesList(CATEGORIZATION);
                set_list(start_tv, finish_tv, FILTER);

                break;

            default:
                break;
        }
    }

    void set_calendar_term(String year, String month, String day, TextView set_tv, int flag, int flag2) {
        int year_ = Integer.parseInt(year);
        int month_ = Integer.parseInt(month);
        int day_ = 0;
        my_calendar.set(Calendar.MONTH, month_ - 1);
        if (flag == 1) {//right btn
            day_ = Integer.parseInt(day) + 1;
            if (day_ > my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day_ = 1;
                month_ += 1;
            }
            if (month_ > 12) {
                year_ += 1;
                month_ = 1;
            }
            if (day_ < 10 && month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + ".0" + day_);
            } else if (day_ < 10) {
                set_tv.setText(year_ + "." + month_ + ".0" + day_);

            } else if (month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + "." + day_);
            } else {

                set_tv.setText(year_ + "." + month_ + "." + day_);
            }
        } else if (flag == 0) {//left btn
            day_ = Integer.parseInt(day) - 1;
            if (day_ < 1) {
                day_ = my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                month_ -= 1;
            }
            if (month_ < 1) {
                year_ -= 1;
                month_ = 12;
            }
            if (day_ < 10 && month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + ".0" + day_);
            } else if (day_ < 10) {
                set_tv.setText(year_ + "." + month_ + ".0" + day_);

            } else if (month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + "." + day_);
            } else {
                set_tv.setText(year_ + "." + month_ + "." + day_);
            }
        }
        if (flag2 == 1) {
            adapter.getStartYear(String.valueOf(year_));
            adapter.getStartMonth(String.valueOf(month_));
            adapter.getStartDay(String.valueOf(day_));
        } else if (flag2 == 2) {
            adapter.getFinishYear(String.valueOf(year_));
            adapter.getFinishMonth(String.valueOf(month_));
            adapter.getFinishDay(String.valueOf(day_));
        }
        adapter.notifyDataSetChanged();
    }


    public void sales_list_filter_Click(View v) {
        switch (v.getId()) {
            case R.id.sales_list_day_btn:
                set_list(start_tv, finish_tv, 0);
                set_enabled_btn(b1, b2, b3);
                FILTER = 0;
                break;
            case R.id.sales_list_month_btn:
                set_list(start_tv, finish_tv, 1);
                FILTER = 1;
                set_enabled_btn(b2, b1, b3);
                break;
            case R.id.sales_list_year_btn:
                set_list(start_tv, finish_tv, 2);
                FILTER = 2;
                set_enabled_btn(b3, b1, b2);
                break;
        }
    }

    void set_enabled_btn(Button b1, Button b2, Button b3) {
        b1.setEnabled(false);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b1.setBackgroundResource(R.drawable.sale_selec_btn);
        b1.setTextColor(Color.WHITE);
        b2.setBackgroundResource(R.drawable.sale_btn);
        b2.setTextColor(Color.BLACK);
        b3.setBackgroundResource(R.drawable.sale_btn);
        b3.setTextColor(Color.BLACK);
    }

    void set_list(TextView t1, TextView t2, int op) {
        int size_ = node_list.size();
        if (size_ != 0) {
            if (node_list.get(0) == defalt_node) {
                tView.removeNode(defalt_node);
                node_list.clear();
            } else {
                for (int i = 0; i < size_; i++) {
                    tView.removeNode(node_list.get(i));
                }
            }
        }
        node_list.clear();
            if (op == 2) { //년
                ArrayList<String> year_list = new ArrayList<>();
                for (int i1 = 0; i1 < sales_list.size(); i1++) {
                    if (Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) >= Integer.parseInt(start_tv.getText().toString().substring(0, 4) + start_tv.getText().toString().substring(5, 7) + start_tv.getText().toString().substring(8)) &&
                            Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) <= Integer.parseInt(finish_tv.getText().toString().substring(0, 4) + finish_tv.getText().toString().substring(5, 7) + finish_tv.getText().toString().substring(8))) {
                        year_list.add(String.valueOf(sales_list.get(i1).getDate()).substring(0, 4));
                    }
                }
                HashSet hs = new HashSet(year_list);
                ArrayList<String> year_list_ = new ArrayList<String>(hs);
                Ascendingstr ascending = new Ascendingstr();
                Collections.sort(year_list_, ascending);
                for (int i3 = 0; i3 < year_list_.size(); i3++) {
                    ArrayList<Sales> month_list = new ArrayList<>();
                    ArrayList<Sales> month_list_ = (ArrayList<Sales>) getSalesList("date", year_list_.get(i3));
                    for (int j = 0; j < month_list_.size(); j++) {
                        if (Integer.parseInt(month_list_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                Integer.parseInt(month_list_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                            month_list.add(month_list_.get(j));
                        }
                    }
                    RealmResults Ysumlist = realm.where(Sales.class).contains("date", year_list.get(i3)).distinct("date");
                    ArrayList<Sales> Ysumlist_ = new ArrayList<>(Ysumlist);
                    ArrayList<Sales> Ysumlist__ = new ArrayList<>();
                    for (int j = 0; j < Ysumlist_.size(); j++) {
                        if (Integer.parseInt(Ysumlist_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                Integer.parseInt(Ysumlist_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                            Ysumlist__.add(Ysumlist_.get(j));
                        }
                    }
                    long Ysum = 0;
                    for (int j2 = 0; j2 < Ysumlist__.size(); j2++) {
                        Ysum += Ysumlist__.get(j2).getSum();
                    }
                    node_list.add(new TreeNode(new SalesAdpater.TreeItem(year_list_.get(i3).substring(0, 4) + "년", Ysum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                    for (int i4 = 0; i4 < month_list.size(); i4++) {
                        if (i4 == month_list.size() - 1) {
                            ArrayList<Sales> day_list = new ArrayList<>();
                            ArrayList<Sales> day_list_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(month_list.get(i4).getDate()).substring(0, 6));
                            for (int j = 0; j < day_list_.size(); j++) {
                                if (Integer.parseInt(day_list_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                        Integer.parseInt(day_list_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                    day_list.add(day_list_.get(j));
                                }
                            }
                            RealmResults sumlist = realm.where(Sales.class).contains("date", month_list.get(i4).getDate().substring(0, 6)).distinct("date");
                            ArrayList<Sales> sumlist_ = new ArrayList<>(sumlist);
                            ArrayList<Sales> sumlist__ = new ArrayList<>();
                            for (int j = 0; j < sumlist_.size(); j++) {
                                if (Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                        Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                    sumlist__.add(sumlist_.get(j));
                                }
                            }
                            long Msum = 0;
                            for (int j2 = 0; j2 < sumlist__.size(); j2++) {
                                Msum += sumlist__.get(j2).getSum();
                            }
                            node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem(month_list.get(i4).getDate().substring(4, 6) + "월", Msum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                            for (int i5 = 0; i5 < day_list.size(); i5++) {
                                if (i5 == day_list.size() - 1) {
                                    ArrayList<Sales> arrayList = new ArrayList<>();
                                    ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8));
                                    for (int j = 0; j < arrayList_.size(); j++) {
                                        if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                                Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                            arrayList.add(arrayList_.get(j));
                                        }
                                    }
                                    Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8)).sum("sum");
                                    node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list.get(i5).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                    for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                        node_list.get(i3).getChildren().get(i4).getChildren().get(i5).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i6).getName(), arrayList.get(i6).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                    }
                                } else {
                                    if (String.valueOf(day_list.get(i5).getDate()).substring(0, 8).equals(String.valueOf(day_list.get(i5 + 1).getDate()).substring(0, 8))) {
                                        node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem("", "")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                    } else {
                                        ArrayList<Sales> arrayList = new ArrayList<>();
                                        ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8));
                                        for (int j = 0; j < arrayList_.size(); j++) {
                                            if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                                    Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                                arrayList.add(arrayList_.get(j));
                                            }
                                        }
                                        Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8)).sum("sum");
                                        node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list.get(i5).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                        for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                            node_list.get(i3).getChildren().get(i4).getChildren().get(i5).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i6).getName(), arrayList.get(i6).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                        }
                                    }
                                }

                            }
                        } else {
                            if (String.valueOf(month_list.get(i4).getDate()).substring(0, 6).equals(String.valueOf(month_list.get(i4 + 1).getDate()).substring(0, 6))) {
                                node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem("", "")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                            } else {
                                ArrayList<Sales> day_list = new ArrayList<>();
                                ArrayList<Sales> day_list_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(month_list.get(i4).getDate()).substring(0, 8));
                                for (int j = 0; j < day_list_.size(); j++) {
                                    if (Integer.parseInt(day_list_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                            Integer.parseInt(day_list_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                        day_list.add(day_list_.get(j));
                                    }
                                }
                                RealmResults sumlist = realm.where(Sales.class).contains("date", month_list_.get(i4).getDate().substring(0, 6)).distinct("date");
                                ArrayList<Sales> sumlist_ = new ArrayList<>(sumlist);
                                ArrayList<Sales> sumlist__ = new ArrayList<>();
                                for (int j = 0; j < sumlist_.size(); j++) {
                                    if (Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                            Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                        sumlist__.add(sumlist_.get(j));
                                    }
                                }
                                long Msum = 0;
                                for (int j2 = 0; j2 < sumlist__.size(); j2++) {
                                    Msum += sumlist__.get(j2).getSum();
                                }
                                node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem((month_list.get(i4).getDate()).substring(4, 6) + "월", Msum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                for (int i5 = 0; i5 < day_list.size(); i5++) {
                                    if (i5 == day_list.size() - 1) {
                                        ArrayList<Sales> arrayList = new ArrayList<>();
                                        ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8));
                                        for (int j = 0; j < arrayList_.size(); j++) {
                                            if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                                    Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                                arrayList.add(arrayList_.get(j));
                                            }
                                        }
                                        Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list.get(i5).getDate()).substring(0, 8)).sum("sum");

                                        node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list.get(i5).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                        for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                            node_list.get(i3).getChildren().get(i4).getChildren().get(i5).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i6).getName(), arrayList.get(i6).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                        }
                                    } else {
                                        if (String.valueOf(day_list.get(i5).getDate()).substring(0, 8).equals(String.valueOf(day_list.get(i5 + 1).getDate()).substring(0, 8))) {
                                            node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem("", "")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                        } else {
                                            ArrayList<Sales> arrayList = new ArrayList<>();
                                            ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list.get(i4).getDate()).substring(0, 8));
                                            for (int j = 0; j < arrayList_.size(); j++) {
                                                if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                                        Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                                    arrayList.add(arrayList_.get(j));
                                                }
                                            }
                                            Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list.get(i4).getDate()).substring(0, 8)).sum("sum");
                                            node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list.get(i5).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                                node_list.get(i3).getChildren().get(i4).getChildren().get(i5).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i6).getName(), arrayList.get(i6).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            } else if (op == 1) { //월
                ArrayList<String> month_list = new ArrayList<>();
                for (int i1 = 0; i1 < sales_list.size(); i1++) {
                    if (Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                            Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                        month_list.add(String.valueOf(sales_list.get(i1).getDate()).substring(0, 6));
                    }
                }
                HashSet hs = new HashSet(month_list);
                ArrayList<String> month_list_ = new ArrayList<String>(hs);
                Ascendingstr ascending = new Ascendingstr();
                Collections.sort(month_list_, ascending);
                Log.d("BEOM25", "month_list : " + month_list_.size());
                for (int i3 = 0; i3 < month_list_.size(); i3++) {
                    Log.d("BEOM26", "i3 : " + i3);
                    ArrayList<Sales> day_list_ = new ArrayList<>();
                    ArrayList<Sales> day_list = (ArrayList<Sales>) getSalesList("date", month_list_.get(i3));
                    for (int j = 0; j < day_list.size(); j++) {
                        if (Integer.parseInt(day_list.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                Integer.parseInt(day_list.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                            day_list_.add(day_list.get(j));
                        }
                    }

                    RealmResults sumlist = realm.where(Sales.class).contains("date", month_list_.get(i3)).distinct("date");
                    ArrayList<Sales> sumlist_ = new ArrayList<>(sumlist);
                    ArrayList<Sales> sumlist__ = new ArrayList<>();
                    for (int j = 0; j < sumlist_.size(); j++) {
                        if (Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                Integer.parseInt(sumlist_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                            sumlist__.add(sumlist_.get(j));
                        }
                    }
                    long Msum = 0;
                    for (int j2 = 0; j2 < sumlist__.size(); j2++) {
                        Msum += sumlist__.get(j2).getSum();
                    }

                    node_list.add(new TreeNode(new SalesAdpater.TreeItem(month_list_.get(i3).substring(4, 6) + "월", Msum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                    Log.d("BEOM25", "day_list : " + day_list_.size());
                    for (int i4 = 0; i4 < day_list_.size(); i4++) {
                        if (i4 == day_list_.size() - 1) {
                            ArrayList<Sales> arrayList = new ArrayList<>();
                            ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list_.get(i4).getDate()).substring(0, 8));
                            for (int j = 0; j < arrayList_.size(); j++) {
                                if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                        Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                    arrayList.add(arrayList_.get(j));
                                }
                            }
                            Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list_.get(i4).getDate()).substring(0, 8)).sum("sum");
                            node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list_.get(i4).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                                node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i5).getName(), arrayList.get(i5).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                            }
                        } else {
                            if (String.valueOf(day_list_.get(i4).getDate()).substring(0, 8).equals(String.valueOf(day_list_.get(i4 + 1).getDate()).substring(0, 8))) {
                                node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem("", "")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                            } else {
                                ArrayList<Sales> arrayList = new ArrayList<>();
                                ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", String.valueOf(day_list_.get(i4).getDate()).substring(0, 8));//, Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)), Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8)));
                                for (int j = 0; j < arrayList_.size(); j++) {
                                    if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                            Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                                        arrayList.add(arrayList_.get(j));
                                    }
                                }
                                Number Dsum = realm.where(Sales.class).contains("date", String.valueOf(day_list_.get(i4).getDate()).substring(0, 8)).sum("sum");
                                node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem(String.valueOf(day_list_.get(i4).getDate()).substring(6, 8) + "일", Dsum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                                    node_list.get(i3).getChildren().get(i4).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i5).getName(), arrayList.get(i5).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                                }
                            }
                        }
                    }
                }

            } else if (op == 0) {//일
                ArrayList<String> day_list = new ArrayList<>();
                for (int i1 = 0; i1 < sales_list.size(); i1++) {

                    if (Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                            Integer.parseInt(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                        day_list.add(String.valueOf(sales_list.get(i1).getDate()).substring(0, 8));

                    }
                }
                HashSet hs = new HashSet(day_list);
                ArrayList<String> day_list_ = new ArrayList<String>(hs);
                Ascendingstr ascending = new Ascendingstr();
                Collections.sort(day_list_, ascending);
                for (int i3 = 0; i3 < day_list_.size(); i3++) {
                    ArrayList<Sales> arrayList = new ArrayList<>();
                    ArrayList<Sales> arrayList_ = (ArrayList<Sales>) getSalesList("date", day_list_.get(i3));//, Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)), Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8)));
                    for (int j = 0; j < arrayList_.size(); j++) {
                        if (Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) >= Integer.parseInt(t1.getText().toString().substring(0, 4) + t1.getText().toString().substring(5, 7) + t1.getText().toString().substring(8)) &&
                                Integer.parseInt(arrayList_.get(j).getDate().substring(0, 8)) <= Integer.parseInt(t2.getText().toString().substring(0, 4) + t2.getText().toString().substring(5, 7) + t2.getText().toString().substring(8))) {
                            arrayList.add(arrayList_.get(j));
                        }
                    }
                    Number sum = realm.where(Sales.class).contains("date", day_list_.get(i3)).sum("sum");
                    node_list.add(new TreeNode(new SalesAdpater.TreeItem(day_list_.get(i3).substring(4, 6) + "월" + day_list_.get(i3).substring(6, 8) + "일", sum + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        node_list.get(i3).addChild(new TreeNode(new SalesAdpater.TreeItem(arrayList.get(i4).getName(), arrayList.get(i4).getSum() + "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                    }
                }
            }

            for (int i = 0; i < node_list.size(); i++) {
                if (node_list.get(i).getParent() == null) {
                    tView.addNode(root, node_list.get(i));
                }
            }

    }

    class Ascendingstr implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public List<Sales> getSalesList(final String table, final String data) {
        List<Sales> list = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Sales> results = realm
                    .where(Sales.class).contains(table, data)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public List<Sales> getAllSalesList(int categol) {
        List<Sales> list = new ArrayList<>();
        if(categol == 0) {
            try {
                realm = Realm.getDefaultInstance();
                RealmResults<Sales> results = realm
                        .where(Sales.class)
                        .findAll();
                list.addAll(realm.copyFromRealm(results));
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
            return list;
        }else if(categol == 1) {
            try {
                realm = Realm.getDefaultInstance();
                RealmResults<Sales> results = realm
                        .where(Sales.class).equalTo("pay",1).equalTo("pay", 2)
                        .findAll();
                list.addAll(realm.copyFromRealm(results));
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }

            return list;
        }
            else {
            try {
                realm = Realm.getDefaultInstance();
                RealmResults<Sales> results = realm
                        .where(Sales.class).equalTo("pay",R.integer.ACash).equalTo("pay",R.integer.BCash)
                        .findAll();
                list.addAll(realm.copyFromRealm(results));
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }

            return list;
            }
    }


    public static void insertData(final String date, final String name, final int price, final int pay) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Sales user = realm.createObject(Sales.class, date);
                user.setName(name);
                user.setPay(pay);
                user.setSum(price);
                realm.insert(user);
            }
        });
    }

}
