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

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jisung on 2017. 8. 29..
 */

public class work_itemAdapter extends BaseAdapter {

    ArrayList<Order> list;
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


        TextView date = (TextView)view.findViewById(R.id.work_date);
        TextView client = (TextView)view.findViewById(R.id.client_name);
        final ImageButton msgBtn = (ImageButton)view.findViewById(R.id.send_msg);
        ListView work_list=(ListView)view.findViewById(R.id.work_item_list);
        ImageView work_state = (ImageView)view.findViewById(R.id.work_state);
        Button comBtn = (Button)view.findViewById(R.id.work_comp);
        Button recepitBtn = (Button)view.findViewById(R.id.work_recepit);

        client_itemAdapter adapter = new client_itemAdapter(view.getContext(),itemParser.parserString(list.get(i).getData()));
        work_list.setAdapter(adapter);
        Log.d("test1",list.get(i).getData());
        client.setText(list.get(i).getName());
        date.setText(dateSet.b_date(list.get(i).getDate()));
//        client.setText(list.get(i).getCustom().getName());
        if(list.get(i).isSending())
            msgBtn.setImageResource(R.color.list_item_background);

        work_list.setAdapter(adapter);
        switch (list.get(i).getWork_state()){
            case 0:
//                comBtn.setBackground();
                break;
            case 1:
                comBtn.setClickable(false);
                comBtn.setBackground(context.getDrawable(R.color.list_item_background));
                break;
            case 3:
                comBtn.setClickable(false);
                recepitBtn.setClickable(false);
//                comBtn.setBackground();
//                recepitBtn.setBackground();
                break;
            default:
                break;
        }


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

        comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isAll) {
                    darimiDataCon.updateStateOrder(realm,list.get(i).getDate());

                }
                notifyDataSetChanged();
            }
        });
        recepitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                list.get(i).setWork_state(2);
                if(!isAll) {
                    Log.d("test1",list.get(i).getDate());
                    darimiDataCon.makeSales(realm,list.get(i).getDate(),list.get(i).getName(),list.get(i).getOrderPrice(),list.get(i).getPay());
                    darimiDataCon.removeOrder(realm,list.get(i).getDate());
                    list.remove(i);
                }
                notifyDataSetChanged();
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


}
