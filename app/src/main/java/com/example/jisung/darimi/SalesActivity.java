package com.example.jisung.darimi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity {
    Intent intent;
    TextView time_N;
    String time;

    TextView start_tv, finish_tv;
    TextView year, month;
    GridView calendar_gridview;
    ArrayList<String> day_list = new ArrayList<String>();
    CalendarAdapter adapter;
    Calendar my_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
    }
    void init(){
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView)findViewById(R.id.time);
        time_N.setText(time);

        start_tv = (TextView)findViewById(R.id.sales_start_tv);
        finish_tv = (TextView)findViewById(R.id.sales_finish_tv);
        year = (TextView)findViewById(R.id.sales_year_tv);
        month = (TextView)findViewById(R.id.sales_month_tv);
        calendar_gridview = (GridView)findViewById(R.id.sales_calendar);
        adapter = new CalendarAdapter(day_list, this);


        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        start_tv.setText(curYearFormat.format(date)+"."+curMonthFormat.format(date)+"."+curDayFormat.format(date));
        finish_tv.setText(curYearFormat.format(date)+"."+curMonthFormat.format(date)+"."+curDayFormat.format(date));

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
        adapter.getStartYear(start_tv.getText().toString().substring(0,4));
        adapter.getStartMonth(start_tv.getText().toString().substring(5,7));
        adapter.getStartDay(start_tv.getText().toString().substring(8));
        adapter.getFinishYear(finish_tv.getText().toString().substring(0,4));
        adapter.getFinishMonth(finish_tv.getText().toString().substring(5,7));
        adapter.getFinishDay(finish_tv.getText().toString().substring(8));

    }
    private void setCalendarDate(int year, int month) {
        day_list.add("일");
        day_list.add("월");
        day_list.add("화");
        day_list.add("수");
        day_list.add("목");
        day_list.add("금");
        day_list.add("토");
        my_calendar.set(year, month-1, 1);
        int dayNum = my_calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i++) {
            day_list.add("");
        }
        my_calendar.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            day_list.add("" + (i + 1));
        }
    }

    void open_calendar(){
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(SalesActivity.this);
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.manageA:
                intent = new Intent(this,ManageActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
            case R.id.orderA:
                intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                break;

            case R.id.settingA:
                intent = new Intent(this,SettingActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;

            case R.id.sales_start_left_btn:
                String start_day = start_tv.getText().toString();
                String start_year = start_day.substring(0,4);
                String start_month = start_day.substring(5,7);
                String start_day_of_month = start_day.substring(8);
                int start_prev_year = Integer.parseInt(start_year);
                int start_prev_month = Integer.parseInt(start_month);
                int start_prev_day = Integer.parseInt(start_day_of_month)-1;
                my_calendar.set(Calendar.MONTH, start_prev_day-1);
                if(start_prev_day<1){
                    start_prev_day = my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    start_prev_month -= 1;
                }
                if(start_prev_month<1){
                    start_prev_year -= 1;
                    start_prev_month = 12;
                }
                if(start_prev_day<10 && start_prev_month<10){
                    start_tv.setText(start_prev_year+".0"+start_prev_month+".0"+start_prev_day);
                }
                else if(start_prev_day<10){
                    start_tv.setText(start_prev_year+"."+start_prev_month+".0"+start_prev_day);

                }else if(start_prev_month<10){
                    start_tv.setText(start_prev_year+".0"+start_prev_month+"."+start_prev_day);
                }else{
                    start_tv.setText(start_prev_year+"."+start_prev_month+"."+start_prev_day);
                }
                adapter.getStartYear(String.valueOf(start_prev_year));
                adapter.getStartMonth(String.valueOf(start_prev_month-1));
                adapter.getStartDay(String.valueOf(start_prev_day));
                adapter.notifyDataSetChanged();
                break;
            case R.id.sales_start_right_btn:
                break;

            case R.id.sales_finish_left_btn:
                String finish_day = finish_tv.getText().toString();
                String finish_year = finish_day.substring(0,4);
                String finish_month = finish_day.substring(5,7);
                String finish_day_of_month = finish_day.substring(8);
                int finish_prev_year = Integer.parseInt(finish_year);
                int finish_prev_month = Integer.parseInt(finish_month);
                int finish_prev_day = Integer.parseInt(finish_day_of_month)-1;
                my_calendar.set(Calendar.MONTH, finish_prev_month-1);
                if(finish_prev_day<1){
                    finish_prev_day = my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    finish_prev_month -= 1;
                }
                if(finish_prev_month<1){
                    finish_prev_year -= 1;
                    finish_prev_month = 12;
                }
                if(finish_prev_day<10 && finish_prev_month<10){
                    finish_tv.setText(finish_prev_year+".0"+finish_prev_month+".0"+finish_prev_day);
                }
                else if(finish_prev_day<10){
                    finish_tv.setText(finish_prev_year+"."+finish_prev_month+".0"+finish_prev_day);

                }else if(finish_prev_month<10){
                    finish_tv.setText(finish_prev_year+".0"+finish_prev_month+"."+finish_prev_day);
                }else{
                    finish_tv.setText(finish_prev_year+"."+finish_prev_month+"."+finish_prev_day);
                }
                adapter.getFinishYear(String.valueOf(finish_prev_year));
                adapter.getFinishMonth(String.valueOf(finish_prev_month-1));
                adapter.getFinishDay(String.valueOf(finish_prev_day));
                adapter.notifyDataSetChanged();
                break;
            case R.id.sales_finish_right_btn:
//                String[] finish_day = finish_tv.getText().toString().split(".");
//                String finish_year = finish_day[0];
//                String finish_month = finish_day[1];
//                String finish_day_of_month = finish_day[2];
                String finish_day_ = finish_tv.getText().toString();
                String finish_year_ = finish_day_.substring(0,4);
                String finish_month_ = finish_day_.substring(5,7);
                String finish_day_of_month_ = finish_day_.substring(8);
                int finish_next_year = Integer.parseInt(finish_year_);
                int finish_next_month = Integer.parseInt(finish_month_);
                int finish_next_day = Integer.parseInt(finish_day_of_month_)+1;
                my_calendar.set(Calendar.MONTH, finish_next_month-1);
                if(finish_next_day>my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    finish_next_day = 1;
                    finish_next_month += 1;
                }
                if(finish_next_month>12){
                    finish_next_year += 1;
                    finish_next_month = 1;
                }
                if(finish_next_day<10 && finish_next_month<10){
                finish_tv.setText(finish_next_year+".0"+finish_next_month+".0"+finish_next_day);
                }
                else if(finish_next_day<10){
                    finish_tv.setText(finish_next_year+"."+finish_next_month+".0"+finish_next_day);

                }else if(finish_next_month<10){
                    finish_tv.setText(finish_next_year+".0"+finish_next_month+"."+finish_next_day);
                }else{

                    finish_tv.setText(finish_next_year+"."+finish_next_month+"."+finish_next_day);
                }
                adapter.getFinishYear(String.valueOf(finish_next_year));
                adapter.getFinishMonth(String.valueOf(finish_next_month-1));
                adapter.getFinishDay(String.valueOf(finish_next_day));
                adapter.notifyDataSetChanged();
                break;

            case R.id.sales_calendar_left_btn:
                day_list.clear();
                int prev_month = Integer.parseInt(month.getText().toString())-1;
                int prev_year = Integer.parseInt(year.getText().toString());
                if(prev_month<1){
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
                int next_month = Integer.parseInt(month.getText().toString())+1;
                int next_year = Integer.parseInt(year.getText().toString());
                if(next_month>12){
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
}
