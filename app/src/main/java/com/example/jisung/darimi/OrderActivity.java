package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.SimpleTimeZone;

import io.realm.Realm;
import io.realm.RealmList;
import it.sephiroth.android.library.widget.HListView;

public class OrderActivity extends AppCompatActivity {
    private TextView time_N,total,order_time,item_total_num;
    private EditText client_name,client_num;
    private Button editActBtn;
    private String today_date,today_time;
    private Intent intent;
    int total_Price=0,total_item;
    Boolean edit_act = false;

    private ArrayList<Item> item_list;
    private ArrayList<Items> selectItems_list;
    private ArrayList<Categol> cate_list;

    private SelectItemAdapter selected_adapter;
    private CateAdapter cate_adapter;
    private ItemBaseAdapter item_adapter;

    private GridView item_view;
    private ListView sele_view;
    private HListView cate_view;
    private Display display;

    private ArrayList<Item> All_item;
    private ArrayList<Item> mItemArray;
    private DragListView mDragListView;
    private Button orderBtn;

    private int payState =0;

    private Custom custom;

    int tmp=0;
    int cate_tmp;


    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();

        item_adapter.notifyDataSetChanged();
        cate_adapter.notifyDataSetChanged();
//        mDragListView.setDragEnabled(true);
        getObjectData();


        item_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    nowTime();
                    order_time.setText(today_date);
                    total_Price += Integer.parseInt(item_list.get(i).getPrice());
                    total.setText(total_Price+"");
                    item_total_num.setText(++total_item + "벌");

                    Log.d("testset","1");
                    for (int j = 0; j < selectItems_list.size(); j++) {
                        Log.d("testset","2");
                        if (selectItems_list.get(j).getItem().getName().equals(item_list.get(i).getName())) {
                            selectItems_list.get(j).setItem_num(selectItems_list.get(j).getItem_num() + 1);
                            selected_adapter.notifyDataSetChanged();//변동사항 보여줌
                            Log.d("testset","3");
                            return;//주문 품목에 동일한 아이템이 있는 경우 숫자를 하나 증가한다
                        }
                    }
                Log.d("testset","4");
                    selectItems_list.add(new Items(item_list.get(i), 1));//없는 경우 새로추가한다
                    selected_adapter.notifyDataSetChanged();//변동사항 보여줌
                Log.d("testset","5");
                }

        });


//        sele_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(OrderActivity.this, "test", Toast.LENGTH_SHORT).show();
//            }
//        });
        sele_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(OrderActivity.this, "this", Toast.LENGTH_SHORT).show();
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                darimiDataCon.makeOrder(realm,itemParser.parserList(selectItems_list),dateKey(),client_num.getText().toString(),client_name.getText().toString(),payState);

                Intent intent = new Intent(OrderActivity.this,SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private boolean isinitInstall(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean is = pref.getBoolean("init",true);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("init",false);
        editor.commit();
        return is;
    }

    void init(){

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        if(isinitInstall()) {
            darimiDataInit.categolDataInit(realm);
            darimiDataInit.itemDataInit(realm);
        }

        orderBtn = (Button)findViewById(R.id.order_btn);

        client_name=(EditText)findViewById(R.id.client_name_E);
        client_num = (EditText)findViewById(R.id.client_number_E);

        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        time_N = (TextView)findViewById(R.id.time);
        total = (TextView)findViewById(R.id.selected_total);
        order_time = (TextView)findViewById(R.id.item_order_time);
        item_total_num = (TextView)findViewById(R.id.item_total_num);

        item_list = new ArrayList<Item>();
        selectItems_list = new ArrayList<Items>();
        cate_list = new ArrayList<Categol>(realm.where(Categol.class).findAll());
        All_item=new ArrayList<Item>(realm.where(Item.class).findAll().sort("seq"));


        selected_adapter = new SelectItemAdapter(selectItems_list,this,item_total_num,total);
        cate_adapter =  new CateAdapter(cate_list,this);
        item_adapter = new ItemBaseAdapter(item_list,this,realm);

        sele_view = (ListView)findViewById(R.id.selected_list);
        cate_view = (HListView)findViewById(R.id.cate_list);
        item_view = (GridView)findViewById(R.id.item_list);

        sele_view.setAdapter(selected_adapter);
        cate_view.setAdapter(cate_adapter);
        item_view.setAdapter(item_adapter);


        mDragListView = (DragListView)findViewById(R.id.drag_list_view);

            for(int j=0;j<All_item.size();j++){
                if(All_item.get(j).isMark())
                    item_list.add(All_item.get(j));
            }

        cate_view.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView)view.findViewById(R.id.cate_name);
                t.setBackgroundColor(getResources().getColor(R.color.Gray));
                realm.beginTransaction();
                cate_list.get(tmp).setChoose(false);
                cate_list.get(i).setChoose(true);
                item_list.clear();
                Collections.sort(All_item,new sortWorks());
                if(cate_list.get(i).getId()==0){
                    for(int j=0;j<All_item.size();j++){
                        if(All_item.get(j).isMark())
                            item_list.add(All_item.get(j));
                    }
                }
                else{
                    for(int j=0;j<All_item.size();j++){
                        if(All_item.get(j).getC_id()==cate_list.get(i).getId())
                            item_list.add(All_item.get(j));
                    }
                }
                realm.commitTransaction();
                cate_adapter.notifyDataSetChanged();
                item_adapter.notifyDataSetChanged();
                tmp = i;
            }
        });


        //카테고리(가로) 리스트 뷰 설정

        timesetM();
        //시간 설정

        sele_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Log.d("orderid","click");

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.beginTransaction();
        cate_list.get(0).setChoose(true);
        for (int i=1;i<cate_list.size();i++){
            cate_list.get(i).setChoose(false);
        }
        realm.commitTransaction();
    }

    void getObjectData(){

    }



    void testObjectAdd(){

    }
    void DragListSetting(){
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        Log.d("test11","2");
        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {

            @Override
            public void onItemDragStarted(int position) {
                Log.d("test11","8");

                Log.d("test11","9");

                Toast.makeText(OrderActivity.this, "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                darimiDataCon.updateItemSeq(realm,item_list.get(fromPosition).getName(),item_list.get(toPosition).getName());
                if (fromPosition != toPosition) {
                    Toast.makeText(OrderActivity.this, "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupGridVerticalRecyclerView();
    }

    private void setupGridVerticalRecyclerView() {
        mDragListView.setLayoutManager(new GridLayoutManager(this, 3));
        ItemAdapter listAdapter = new ItemAdapter(item_list, R.layout.order_item, R.id.item_layout, true);
        listAdapter.realm = realm;
        listAdapter.cate = tmp;
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(true);
        mDragListView.setCustomDragItem(null);

    }

    void timesetM(){
        nowTime();
        time_N.setText(today_date);
    }

    void onClick(View v){
        Log.d("orderid",v.getId()+"");
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

    public void EonClick(View v){
        if(v.getId()==R.id.item_edit_btn||v.getId()==R.id.edit_area){
            if(!edit_act)
                item_list.add(new Item(" "," ",5,R.drawable.add_tmp,false));
            edit_act=true;//편집 활성화
            mDragListView.setVisibility(View.VISIBLE);
            item_view.setVisibility(View.INVISIBLE);
            edit_act = true;

            DragListSetting();

        }
        else{
            if(edit_act)
                item_list.remove(item_list.size()-1);
            mDragListView.setVisibility(View.INVISIBLE);
            item_view.setVisibility(View.VISIBLE);
            item_adapter.notifyDataSetChanged();

            edit_act=false;//그 외의 부분인 경우 비활성화
        }
    }

    void nowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatDate = sdfNow.format(date);
        today_date =formatDate.substring(0,4)+"."+formatDate.substring(5,7)+"."+formatDate.substring(8,10)+"";
        today_time = formatDate.substring(11,13)+":"+formatDate.substring(14,16);
    }//날짜와 시간 문자열로 받아옴
    String dateKey(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdfNow.format(date);
    }
    class sortWorks implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return (o1.getSeq()>o2.getSeq())?1:0;
        }
    }
}
