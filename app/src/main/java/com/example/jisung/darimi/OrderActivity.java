package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

import it.sephiroth.android.library.widget.HListView;

public class OrderActivity extends AppCompatActivity {
    private TextView time_N,total,order_time;
    private Button editActBtn;
    private String today_date,today_time;
    private Intent intent;
    int total_Price=0;
    Boolean edit_act = false;

    private ArrayList<Item> item_list;
    private ArrayList<SelectItem> selectItems_list;
    private ArrayList<Categol> cate_list;

    private ItemAdapter item_adapter;
    private SelectItemAdapter selected_adapter;
    private CateAdapter cate_adapter;

    private GridView item_view;
    private ListView sele_view;
    private HListView cate_view;
    private Display display;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        getObjectData();


        item_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(edit_act){//편집 활성화 시
                    View e_view = View.inflate(view.getContext(), R.layout.item_setting, null);   //뷰 가져오기

                    final Dialog dialog = new Dialog(view.getContext()); //대화상자 생성
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(e_view); //대화상자 뷰 설정
                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.width = (int) (display.getWidth() * 0.8);
                    params.height = (int) (display.getHeight() * 0.6);
                    dialog.getWindow().setAttributes(params);//대화상자 크기 설정

                    final EditText eitem_name = (EditText)e_view.findViewById(R.id.item_name);
                    final EditText eitem_price = (EditText)e_view.findViewById(R.id.item_price);
                    Button selcet_img = (Button)e_view.findViewById(R.id.change_img);
                    Button comple = (Button)e_view.findViewById(R.id.edit_com);
                    //대화상자 초기화

                    eitem_name.setText(item_list.get(i).getName());
                    eitem_price.setText(item_list.get(i).getPrice());

                    selcet_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*
                            이미지 선택 및 저장
                            */

                        }
                    });//파일 선택
                    comple.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            item_list.get(i).setName(eitem_name.toString());
                            item_list.get(i).setPrice(eitem_price.toString());
                            //클릭시 아이템 변경
                            /*
                            데이터 베이스 전송
                             */

                        }
                    });


                    dialog.show();
                }

                else {
                    nowTime();
                    order_time.setText(today_date + today_date);
                    total_Price += Integer.parseInt(item_list.get(i).getPrice());
                    total.setText(total_Price + "원");

                    for (int j = 0; j < selectItems_list.size(); j++) {
                        if (selectItems_list.get(j).getItem().getName().equals(item_list.get(i).getName())) {
                            selectItems_list.get(j).setNum(selectItems_list.get(j).getNum() + 1);
                            selected_adapter.notifyDataSetChanged();//변동사항 보여줌

                            return;//주문 품목에 동일한 아이템이 있는 경우 숫자를 하나 증가한다
                        }
                    }
                    selectItems_list.add(new SelectItem(item_list.get(i), 1));//없는 경우 새로추가한다
                    selected_adapter.notifyDataSetChanged();//변동사항 보여줌
                }
            }
        });


    }
    void init(){

        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        time_N = (TextView)findViewById(R.id.time);
        total = (TextView)findViewById(R.id.selected_total);
        order_time = (TextView)findViewById(R.id.item_order_time);

        item_list = new ArrayList<Item>();
        selectItems_list = new ArrayList<SelectItem>();
        cate_list = new ArrayList<Categol>();
        cate_list.add(new Categol("1번"));
        cate_list.add(new Categol("2번"));
        cate_list.add(new Categol("1번"));
        cate_list.add(new Categol("2번"));

        item_adapter = new ItemAdapter(item_list,this);
        selected_adapter = new SelectItemAdapter(selectItems_list,this);
        cate_adapter =  new CateAdapter(cate_list,this);

        item_view = (GridView)findViewById(R.id.item_list);
        sele_view = (ListView)findViewById(R.id.selected_list);
        cate_view = (HListView)findViewById(R.id.cate_list);

        item_view.setAdapter(item_adapter);
        sele_view.setAdapter(selected_adapter);
        cate_view.setAdapter(cate_adapter);

        //리스트 뷰 설정

        timesetM();
        //시간 설정

    }


    void getObjectData(){

    }



    void timesetM(){
        nowTime();
        time_N.setText(today_date);
    }

    void onClick(View v){
        switch (v.getId()){
            case R.id.manageA:
                intent = new Intent(this,ManageActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this,SalesActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            case R.id.settingA:
                intent = new Intent(this,SettingActivity.class);
                intent.putExtra("time",today_date);
                startActivity(intent);
                break;
            default:
                break;
        }//화면 변경
    }

    void EonClick(View v){
        if(v.getId()==R.id.item_edit_btn||v.getId()==R.id.edit_area){
            edit_act=true;//편집 활성화
        }
        else{
            edit_act=false;//그 외의 부분인 경우 비활성화
        }
    }
    void nowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatDate = sdfNow.format(date);
        today_date =formatDate.substring(0,4)+"년 "+formatDate.substring(5,7)+"월 "+formatDate.substring(8,10)+"일";
        today_time = formatDate.substring(11,13)+":"+formatDate.substring(14,16);
    }//날짜와 시간 문자열로 받아옴
}
