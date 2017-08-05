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

import java.util.ArrayList;
import java.util.Calendar;

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
        Integer today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String todays = String.valueOf(today);
        Log.d("BEOM", "getYear : " + get_Title_Year);
        Log.d("BEOM", "Calender.Year : " + Calendar.getInstance().get(Calendar.YEAR));
        Log.d("BEOM", "getMonth : " + get_Title_Month);
        Log.d("BEOM", "Calender.Month : " + Calendar.getInstance().get(Calendar.MONTH));
        Log.d("BEOM", "todays : " + todays);
        Log.d("BEOM", "getItem : " + getItem(i));
        if (todays.equals(getItem(i)) && get_Title_Year.equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
                && get_Title_Month.equals(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)))) {
            Log.d("BEOM", "TODAY COLOR CHANGE");
            calendar_day.setBackgroundColor(Color.BLUE);
        }else if(get_Finish_Year.equals(get_Title_Year) && get_Finish_Month.equals(get_Title_Month) && get_Finish_Day.equals(getItem(i))){
            calendar_day.setBackgroundColor(Color.RED);
//            for(int j = Integer.parseInt(get_Start_Day); j < Integer.parseInt(get_Finish_Day); j++){
//                Log.d("BEOM1", "j = " + j);
//                Log.d("BEOM1", "getItem = " + Integer.parseInt((String) getItem(i)));
//                if(j==Integer.parseInt((String) getItem(i))){
//                    calendar_day.setBackgroundColor(Color.RED);
//                }
//            }

        }
        else {
            calendar_day.setBackgroundColor(Color.WHITE);
        }
        for(int j = Integer.parseInt(get_Start_Day); j < Integer.parseInt(get_Finish_Day); j++) {
            Log.d("BEOM1", "j = " + j);
            if (String.valueOf(j).equals(getItem(i))) {
                calendar_day.setBackgroundColor(Color.RED);
            }
        }
//        if (get_Finish_Year.equals(get_Title_Year) && get_Finish_Month.equals(get_Title_Month) && get_Finish_Day.equals(getItem(i))) {
////            for(int j = Integer.parseInt(get_Start_Day); j < Integer.parseInt(get_Finish_Day); j++) {
//            calendar_day.setBackgroundColor(Color.RED);
//        }
//        }
        return view;
    }

    public void getTitleYear(String year) {
        this.get_Title_Year = year;
    }

    public void getTitleMonth(String month) {
        int temp = Integer.parseInt(month) - 1;
        month = String.valueOf(temp);
        this.get_Title_Month = month;
    }

    public void getFinishYear(String year) {
        this.get_Finish_Year = year;
    }

    public void getFinishMonth(String month) {
        this.get_Finish_Month = month;
    }

    public void getFinishDay(String day) {
        this.get_Finish_Day = day;
    }
    public void getStartYear(String year) {
        this.get_Start_Year = year;
    }

    public void getStartMonth(String month) {
        this.get_Start_Month = month;
    }

    public void getStartDay(String day) {
        this.get_Start_Day = day;
    }
}
