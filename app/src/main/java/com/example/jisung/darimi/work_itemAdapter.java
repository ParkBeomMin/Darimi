package com.example.jisung.darimi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jisung on 2017. 8. 29..
 */

public class work_itemAdapter extends BaseAdapter {

    ArrayList<Order> list,nextlist;
    work_nameAdapter noti;
    Context context;
    Realm realm;
    boolean isAll=false;


    public work_itemAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null)
            view = inflater.inflate(R.layout.work_item_list, null);


        final TextView date = (TextView)view.findViewById(R.id.work_date);
        TextView client = (TextView)view.findViewById(R.id.client_name);
        final ImageButton msgBtn = (ImageButton)view.findViewById(R.id.send_msg);
        ListView work_list=(ListView)view.findViewById(R.id.work_item_list);
        TextView pay = (TextView)view.findViewById(R.id.pay_state);
        Button state = (Button)view.findViewById(R.id.stateBtn);
//        Log.d("test111",list.get(i).getPay()+"pau");

        switch (list.get(i).getPay()){
            case 4:
                pay.setText("후불");
                break;
            case 3:
                pay.setText("후불");
                break;
            case 1:
                pay.setText("선불");
                break;
            case 2:
                pay.setText("선불");
                break;
            default:
                break;
        }

        if(list.get(i).getWork_state()==0)
            state.setText("작업완료");
        else
            state.setText("지불 & 수령완료");

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getWork_state()==0){
                    darimiDataCon.updateStateOrder(realm,list.get(i).getDate());
                    nextlist.add(list.get(i));
                    list.remove(list.get(i));
                }
                else{
                    Log.d("test111",list.get(i).getDate()+list.get(i).getName()+list.get(i).getOrderPrice()+list.get(i).getPay());
                    noti.listChange(list.get(i));
                    darimiDataCon.makeSales(realm,list.get(i).getDate(),list.get(i).getName(),list.get(i).getOrderPrice(),list.get(i).getPay());
                    darimiDataCon.removeOrder(realm,list.get(i).getDate());
                    list.remove(i);
                }
                notifyDataSetChanged();

            }
        });

        client_itemAdapter adapter = new client_itemAdapter(view.getContext(),itemParser.parserString(list.get(i).getData()));
        work_list.setAdapter(adapter);
        Log.d("test1",list.get(i).getData());
        client.setText(list.get(i).getName());
        date.setText(dateSet.b_date(list.get(i).getDate()));

        if(list.get(i).isSending())
            msgBtn.setImageResource(R.color.list_item_background);

        work_list.setAdapter(adapter);



        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).getWork_state() == 1) {
                    String msg = "세탁이 완료되었습니다. 수령바랍니다.";
                    sendSMS(list.get(i).getCall(), msg);//미전송 케이스 처리
                    msgBtn.setBackgroundResource(R.color.list_item_background);
                    darimiDataCon.updateMsgOrder(realm,list.get(i).getDate());
                    Toast.makeText(context, "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
    public void sendSMS(String smsNumber, String smsText){
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }
    public void sortToName(String name){
        int tmp=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(name))
                Collections.swap(list,tmp++,i);
        }
    }


}
