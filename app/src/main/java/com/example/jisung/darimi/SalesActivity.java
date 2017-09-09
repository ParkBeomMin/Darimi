package com.example.jisung.darimi;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;

public class SalesActivity extends AppCompatActivity {
    Realm realm;
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
    TreeNode none_node;
    ArrayList<TreeNode> node_list = new ArrayList<TreeNode>();

    ArrayList<Order> sales_list ;//= new ArrayList<Sales>();

//    ArrayList<ArrayList<ArrayList<String>>> test1 = new ArrayList<>();
//    ArrayList<ArrayList<String>> test2 = new ArrayList<>();
//    ArrayList<String> test3 = new ArrayList<>();
    ArrayList<Test1> test1 = new ArrayList<>();
    ArrayList<Test2> test2 = new ArrayList<>();
    ArrayList<String> test3 = new ArrayList<>();
    int FILTER = 0;

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
//        sales_list = new ArrayList<Order>(realm.where(Order.class).findAll());
        test3.add("shirts");
        test3.add("pants");
        test2.add(new Test2("박범민",test3));
        test1.add(new Test1("20170910",test2));


        AscendingObj ascending = new AscendingObj();
//        Collections.sort(sales_list, ascending);

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
//        int dayNum = my_calendar.get(Calendar.DAY_OF_WEEK);
//        for (int i = 1; i < dayNum; i++) {
//            day_list.add("");
//        }
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
//        for (int i = 0; i < sales_list.size(); i++) {
//            if (String.valueOf(sales_list.get(i).getDate()).substring(6).equals(curDayFormat.format(date))) {
//                defalt_node = new TreeNode(new SalesAdpater.TreeItem(curDayFormat.format(date) + "일", sales_list.get(i).getSale() + "")).setViewHolder(new SalesAdpater(this));
//                TreeNode child0 = new TreeNode(new SalesAdpater.TreeItem(sales_list.get(i).getName(), sales_list.get(i).getSale() + "")).setViewHolder(new SalesAdpater(this));
//                TreeNode child1 = new TreeNode(new SalesAdpater.TreeItem(sales_list.get(i).getItem(), sales_list.get(i).getSale() + "")).setViewHolder(new SalesAdpater(this));
//                defalt_node.addChildren(child0);
//                child0.addChild(child1);
//
//            } else {
//                defalt_node = new TreeNode(new SalesAdpater.TreeItem("내역이 없습니다.", "")).setViewHolder(new SalesAdpater(this));
//            }
//        }
        defalt_node = new TreeNode(new SalesAdpater.TreeItem("내역이 없습니다.", "")).setViewHolder(new SalesAdpater(this));

        root.addChild(defalt_node);
        tView = new AndroidTreeView(this, root);
        containerView = (ViewGroup) findViewById(R.id.sales_list);
        containerView.addView(tView.getView());
        node_list.add(defalt_node);

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
//                tView.removeNode(none_node);
//
//                if(node_list.size() == 0){
//                     none_node = new TreeNode(new SalesAdpater.TreeItem("매출내역없음","")).setViewHolder(new SalesAdpater(SalesActivity.this));
//tView.addNode(root,
//        none_node);
//                }
            }
        });
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
//                tView.removeNode(none_node);
            }
        }
        node_list.clear();
        if (op == 2) { //년
            for (int i = Integer.parseInt(t1.getText().toString().substring(0, 4)); i <= Integer.parseInt(t2.getText().toString().substring(0, 4)); i++) {
                node_list.add(new TreeNode(new SalesAdpater.TreeItem(i + "년", "500,000원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
            }
            for (int i = 0; i < node_list.size(); i++) {
                for (int j = Integer.parseInt(t1.getText().toString().substring(5, 7)); j <= Integer.parseInt(t2.getText().toString().substring(5, 7)); j++) {
                    node_list.get(i).addChild(new TreeNode(new SalesAdpater.TreeItem(j + "월", "500,000원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                }
            }
            for (int i = 0; i < node_list.size(); i++) {
                for (int j = 0; j <= Integer.parseInt(t2.getText().toString().substring(5, 7)) - Integer.parseInt(t1.getText().toString().substring(5, 7)); j++) {
                    for (int k = Integer.parseInt(t1.getText().toString().substring(8)); k <= Integer.parseInt(t2.getText().toString().substring(8)); k++) {
                        node_list.get(i).getChildren().get(j).addChild((new TreeNode(new SalesAdpater.TreeItem(k + "일", "500,000원")).setViewHolder(new SalesAdpater(SalesActivity.this))));
                    }
                }
            }
            for (int i = 0; i < node_list.size(); i++) {
                for (int j = 0; j <= Integer.parseInt(t2.getText().toString().substring(5, 7)) - Integer.parseInt(t1.getText().toString().substring(5, 7)); j++) {
                    for (int k = 0; k <= Integer.parseInt(t2.getText().toString().substring(8)) - Integer.parseInt(t1.getText().toString().substring(8)); k++) {
                        node_list.get(i).getChildren().get(j).getChildren().get(k).addChild(new TreeNode(new SalesAdpater.TreeItem("박범민", "500,000원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
                    }
                }
            }
        } else if (op == 1) { //월


        } else if (op == 0) {//일
            for (int i = 0; i < test1.size(); i++){
                node_list.add(new TreeNode(new SalesAdpater.TreeItem(test1.get(i).getDate() + "년", "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
            for (int i1 = 0; i1 < test2.size(); i1++){
                node_list.get(i).addChild(new TreeNode(new SalesAdpater.TreeItem(test2.get(i1).getName(), "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
            for (int i2 = 0; i2 < test3.size(); i2++) {
                node_list.get(i).getChildren().get(i1).addChild(new TreeNode(new SalesAdpater.TreeItem(test3.get(i2) + "년", "원")).setViewHolder(new SalesAdpater(SalesActivity.this)));
            }
            }
            }

        }

            for (int i = 0; i < node_list.size(); i++) {
                if (node_list.get(i).getParent() == null) {
                    tView.addNode(root, node_list.get(i));
                }
            }

        }

        class AscendingObj implements Comparator<Order> {

            @Override
            public int compare(Order o1, Order o2) {
//            return o1.getDate() >= o2.getDate();
                return String.valueOf(o1.getDate()).compareTo(o2.getDate() + "");
            }

        }

        class Ascendingstr implements Comparator<String> {

            @Override
            public int compare(String o1, String o2) {
//            return o1.getDate() >= o2.getDate();
                return o1.compareTo(o2);
            }

        }


    }
