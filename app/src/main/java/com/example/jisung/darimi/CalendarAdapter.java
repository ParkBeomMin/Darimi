package com.example.jisung.darimi;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by parkbeommin on 2017. 8. 5..
 */

public class CalendarAdapter extends BaseAdapter {
    ArrayList<String> arrayList;
    Context c;
    String get_Title_Year = "", get_Title_Month = "";
    String get_Start_Year = "", get_Start_Month = "", get_Start_Day = "";
    String get_Finish_Year = "", get_Finish_Month = "", get_Finish_Day = "";

    public CalendarAdapter(ArrayList<String> arrayList, Context c) {
        this.arrayList = arrayList;
        this.c = c;
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
            view = inflater.inflate(R.layout.calendar_day, null);
        }
        TextView calendar_day = (TextView) view.findViewById(R.id.calendar_day_tv);
        String day = arrayList.get(i);
        calendar_day.setText(day);
        calendar_day.setTextColor(Color.BLACK);
        if(i==0 || i==7 || i==14 || i==21 || i==28 || i==35 || i==42){
            calendar_day.setTextColor(Color.RED);
        }
        Integer today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String todays = String.valueOf(today);
        if (todays.equals(getItem(i)) && get_Title_Year.equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
                && get_Title_Month.equals(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1))) {
            Log.d("BEOM", "TODAY COLOR CHANGE");
            calendar_day.setBackgroundResource(R.drawable.circle_border);
        }
        else {
            calendar_day.setBackgroundColor(Color.WHITE);
        }
//        for (int j = Integer.parseInt(get_Start_Day); j <= Integer.parseInt(get_Finish_Day); j++) {
//            if ((get_Title_Year.equals(get_Start_Year) && get_Title_Month.equals(get_Start_Month)) &&
//                    (get_Title_Year.equals(get_Finish_Year) && get_Title_Month.equals(get_Finish_Month))) {
//
//                if (String.valueOf(j).equals(getItem(i))) {
//                    calendar_day.setBackgroundResource(R.drawable.circle_border);
//                }
//            }
//        }
        return view;
    }

    public void getTitleYear(String year) {
        this.get_Title_Year = year;
    }

    public void getTitleMonth(String month) {
        int temp = Integer.parseInt(month);
        month = String.valueOf(temp);
        this.get_Title_Month = month;
    }

    public void getFinishYear(String year) {
        this.get_Finish_Year = year;
    }

    public void getFinishMonth(String month) {
        int temp = Integer.parseInt(month);
        month = String.valueOf(temp);
        this.get_Finish_Month = month;
    }

    public void getFinishDay(String day) {
        this.get_Finish_Day = day;
    }

    public void getStartYear(String year) {
        this.get_Start_Year = year;
    }

    public void getStartMonth(String month) {
        int temp = Integer.parseInt(month);
        month = String.valueOf(temp);
        this.get_Start_Month = month;
    }

    public void getStartDay(String day) {
        this.get_Start_Day = day;
    }

    public static long diffOfDate(String begin, String end) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }
}
