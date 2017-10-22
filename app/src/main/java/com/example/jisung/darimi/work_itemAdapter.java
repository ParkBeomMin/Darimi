package com.example.jisung.darimi;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
    TextView txt;
    int payState;
    work_itemAdapter nextAdapter;
ManageActivity m = new ManageActivity();

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
        txt.setText(list.size()+"건");

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

        else{
            msgBtn.setBackgroundResource(R.drawable.msg_2);
            state.setText("지불 & 수령완료");
        }

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getWork_state()==0){
                    darimiDataCon.updateStateOrder(realm,list.get(i).getDate());
                    nextlist.add(list.get(i));
                    list.remove(list.get(i));
                    nextAdapter.notifyDataSetChanged();

                }
                else{
                    payState=list.get(i).getPay();
                    if(payState==4) {
                        View r_view = View.inflate(context, R.layout.paymethod, null);
                        final Dialog dialog = new Dialog(context);
                        Display display;
                        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();


                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(r_view); //대화상자 뷰 설정
                        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                        params.width = (int) (display.getWidth() * 0.43);
                        params.height = (int) (display.getHeight() * 0.3);
                        dialog.getWindow().setAttributes(params);//대화상자 크기 설정


                        dialog.setContentView(r_view);
                        Button card = (Button) r_view.findViewById(R.id.card);
                        Button cash = (Button) r_view.findViewById(R.id.cash);
                        card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                payState = 2;
                                dialog.dismiss();
                            }
                        });
                        cash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                payState = 4;
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                    noti.listChange(list.get(i));
                    Log.d("test22a",list.get(i).getData());
                    darimiDataCon.makeSales(realm,list.get(i).getDate(),list.get(i).getName(),list.get(i).getOrderPrice(),payState);
                    darimiDataCon.removeOrder(realm,list.get(i).getDate());
                    list.remove(i);
                }
                txt.setText(list.size()+"건");
                notifyDataSetChanged();

            }
        });

        client_itemAdapter adapter = new client_itemAdapter(view.getContext(),itemParser.parserString(list.get(i).getData()));
        work_list.setAdapter(adapter);
        Log.d("test1",list.get(i).getData());
        client.setText(list.get(i).getName());
        date.setText(dateSet.b_date(list.get(i).getDate()));

        if(list.get(i).isSending())
            msgBtn.setBackgroundResource(R.drawable.msg_3);

        work_list.setAdapter(adapter);



        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).getWork_state() == 1) {
                    String msg = "[한양세탁소] "+list.get(i).getName()+"고객님 세탁이 완료되었습니다. 세탁물 수령바랍니다.";
                    sendSMS(list.get(i).getCall(), msg,i,msgBtn);//미전송 케이스 처리


//                    Toast.makeText(context, "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
    public void sendSMS(String smsNumber, String smsText,final int j,final ImageButton msgBtn){
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        m.setCustomToast(context, "문자가 전송되었습니다.");
                        msgBtn.setBackgroundResource(R.drawable.msg_3);
                        darimiDataCon.updateMsgOrder(realm,list.get(j).getDate());
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        m.setCustomToast(context, "문자가 전송에 실패했습니다.");break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));


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
