package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import io.realm.Realm;
import it.sephiroth.android.library.widget.HListView;



public class OrderActivity extends AppCompatActivity {
    private TextView time_N, total, order_time, item_total_num;
    private EditText client_name, client_num;
    private Button editActBtn,addBtn;
    private String today_date, today_time;
    private Intent intent;
    int total_Price = 0, total_item;
    Boolean edit_act = false;

    private ArrayList<Item> item_list;
    private ArrayList<Items> selectItems_list;
    private ArrayList<Categol> cate_list;

    ItemAdapter listAdapter;

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

    private Spinner spinner;

    private int payState = 0;

    private Custom custom;

    int tmp = 0;
    int cate=0;
    int catmp;


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
                if(exitEdit())
                    return;
                nowTime();
                order_time.setText(today_date);
                total_Price += Integer.parseInt(item_list.get(i).getPrice());
                total.setText(total_Price + "");
                item_total_num.setText(++total_item + "벌");

                Log.d("testset", "1");
                for (int j = 0; j < selectItems_list.size(); j++) {
                    Log.d("testset", "2");
                    if (selectItems_list.get(j).getItem().getName().equals(item_list.get(i).getName())) {
                        selectItems_list.get(j).setItem_num(selectItems_list.get(j).getItem_num() + 1);
                        selected_adapter.notifyDataSetChanged();//변동사항 보여줌
                        Log.d("testset", "3");
                        return;//주문 품목에 동일한 아이템이 있는 경우 숫자를 하나 증가한다
                    }
                }
                Log.d("testset", "4");
                selectItems_list.add(new Items(item_list.get(i), 1));//없는 경우 새로추가한다
                selected_adapter.notifyDataSetChanged();//변동사항 보여줌
                Log.d("testset", "5");
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
                if(exitEdit())
                    return;

                if (client_num.getText().toString().length() < 10 || client_name.getText().toString().length() < 2) {
                    Toast.makeText(OrderActivity.this, "고객 정보를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (payState == 0) {
                    Toast.makeText(OrderActivity.this, "결재 방법을 선택해주새요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                 darimiDataCon.makeOrder(realm, itemParser.parserList(selectItems_list), dateKey(), client_num.getText().toString(), client_name.getText().toString(), payState);

                Intent intent = new Intent(OrderActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exitEdit())
                    return;
                View e_view = View.inflate(view.getContext(), R.layout.item_setting, null);   //뷰 가져오기
                Display display;

                display = ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                final Dialog dialog = new Dialog(view.getContext()); //대화상자 생성
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(e_view); //대화상자 뷰 설정
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = (int) (display.getWidth() * 0.3);
                params.height = (int) (display.getHeight() * 0.3);
                dialog.getWindow().setAttributes(params);//대화상자 크기 설정
                final EditText eitem_name = (EditText) e_view.findViewById(R.id.edit_name);
                final EditText eitem_price = (EditText) e_view.findViewById(R.id.edit_price);
                spinner=(Spinner)e_view.findViewById(R.id.cateSpiner);
                Button comple = (Button) e_view.findViewById(R.id.edit_com);
                //대화상자 초기화
                catmp=cate;
                if(cate!=0)
                    spinner.setSelection(cate-1);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String str = (String)spinner.getSelectedItem();
                        switch (str){
                            case "상의":
                                catmp =1;
                                break;
                            case "하의":
                                catmp =2;
                                break;
                            case "겉옷":
                                catmp=3;
                                break;
                            case "정장":
                                catmp=4;
                                break;
                            case "신발":
                                catmp=5;
                                break;
                            case "기타":
                                catmp=6;
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                comple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        darimiDataCon.makeItem(realm,view.getContext(),eitem_name.getText().toString(),eitem_price.getText().toString(),R.drawable.images3,catmp);
//                        O.setCustomToast(view.getContext(), "항목이 추가되었습니다.");

                        dialog.dismiss();
                        All_item = new ArrayList<Item>(realm.where(Item.class).findAll().sort("seq"));
                        item_list.clear();
                        for (int j = 0; j < All_item.size(); j++) {
                            if (All_item.get(j).getC_id() == cate_list.get(tmp).getId())
                                item_list.add(All_item.get(j));
                        }
                        //클릭시 아이템 변경
                            /*
                            데이터 베이스 전송
                             */

                    }
                });

                dialog.show();
                return;
            }
        });

    }


    public void searchBtn(View v) {
        if (v.getId() == R.id.number_search)
            client_name.setText(darimiDataCon.findClientName(realm, client_num.getText().toString()));
        else if (v.getId() == R.id.client_search) {
            ArrayList<String> nums = new ArrayList<String>();
            nums = darimiDataCon.findClientCall(realm, client_name.getText().toString());
            if (nums.size() == 0)
                return;
            else if (nums.size() == 1)
                client_num.setText(nums.get(0));
            else {
                View view = View.inflate(this, R.layout.clientnumlist, null);
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(view);
                ListView listView = (ListView) view.findViewById(R.id.numlist);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, nums);
                listView.setAdapter(adapter);
                final ArrayList<String> finalNums = nums;
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        client_num.setText(finalNums.get(i));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    private boolean isinitInstall() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean is = pref.getBoolean("init", true);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("init", false);
        editor.commit();
        return is;
    }

    void init() {

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        if (isinitInstall()) {
            darimiDataInit.itemDataInit(realm);
        }

        orderBtn = (Button) findViewById(R.id.order_btn);

        client_name = (EditText) findViewById(R.id.client_name_E);
        client_num = (EditText) findViewById(R.id.client_number_E);

        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        time_N = (TextView) findViewById(R.id.time);
        total = (TextView) findViewById(R.id.selected_total);
        order_time = (TextView) findViewById(R.id.item_order_time);
        item_total_num = (TextView) findViewById(R.id.item_total_num);

        addBtn = (Button)findViewById(R.id.item_add_btn);
        item_list = new ArrayList<Item>();
        selectItems_list = new ArrayList<Items>();
        cate_list = new ArrayList<Categol>();
        cate_list.add(new Categol("즐겨찾기", 0, true));
        cate_list.add(new Categol("상의", 1, false));
        cate_list.add(new Categol("하의", 2, false));
        cate_list.add(new Categol("겉옷", 3, false));
        cate_list.add(new Categol("정장", 4, false));
        cate_list.add(new Categol("신발", 5, false));
        cate_list.add(new Categol("기타", 6, false));

        All_item = new ArrayList<Item>(realm.where(Item.class).findAll().sort("seq"));

        selected_adapter = new SelectItemAdapter(selectItems_list, this, item_total_num, total);
        cate_adapter = new CateAdapter(cate_list, this);
        item_adapter = new ItemBaseAdapter(item_list, this, realm);

        sele_view = (ListView) findViewById(R.id.selected_list);
        cate_view = (HListView) findViewById(R.id.cate_list);
        item_view = (GridView) findViewById(R.id.item_list);

        sele_view.setAdapter(selected_adapter);
        cate_view.setAdapter(cate_adapter);
        item_view.setAdapter(item_adapter);


        mDragListView = (DragListView) findViewById(R.id.drag_list_view);

        for (int j = 0; j < All_item.size(); j++) {
            if (All_item.get(j).isMark())
                item_list.add(All_item.get(j));
        }

        cate_view.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> adapterView, View view, int i, long l) {
                if(exitEdit())
                    return;
                cate =i;
                TextView t = (TextView) view.findViewById(R.id.cate_name);
                t.setBackgroundColor(getResources().getColor(R.color.Gray));
                cate_list.get(tmp).setChoose(false);
                cate_list.get(i).setChoose(true);
                item_list.clear();
                Collections.sort(All_item, new sortWorks());
                if (cate_list.get(i).getId() == 0) {
                    for (int j = 0; j < All_item.size(); j++) {
                        if (All_item.get(j).isMark())
                            item_list.add(All_item.get(j));
                    }
                } else {
                    for (int j = 0; j < All_item.size(); j++) {
                        if (All_item.get(j).getC_id() == cate_list.get(i).getId())
                            item_list.add(All_item.get(j));
                    }
                }
                Collections.sort(item_list, new sortWorks());
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

                Log.d("orderid", "click");

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.beginTransaction();
        cate_list.get(0).setChoose(true);
        for (int i = 1; i < cate_list.size(); i++) {
            cate_list.get(i).setChoose(false);
        }
        realm.commitTransaction();
    }

    void getObjectData() {

    }


    void testObjectAdd() {

    }

    void DragListSetting() {
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        Log.d("test11", "2");
        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {

            @Override
            public void onItemDragStarted(int position) {


                Toast.makeText(OrderActivity.this, "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                darimiDataCon.changeItemPostion(realm,item_list,fromPosition,toPosition);
                Collections.sort(item_list, new sortWorks());
                All_item = new ArrayList<Item>(realm.where(Item.class).findAll().sort("seq"));
                item_list.clear();
                for (int j = 0; j < All_item.size(); j++) {
                    if (All_item.get(j).getC_id() == cate_list.get(tmp).getId())
                        item_list.add(All_item.get(j));
                }
                if (fromPosition != toPosition) {
                    Toast.makeText(OrderActivity.this, "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupGridVerticalRecyclerView();
    }

    private void setupGridVerticalRecyclerView() {
        mDragListView.setLayoutManager(new GridLayoutManager(this, 3));
        listAdapter = new ItemAdapter(item_list, R.layout.order_item, R.id.item_layout, true);
        listAdapter.realm = realm;
        listAdapter.cate = tmp;
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(true);
        mDragListView.setCustomDragItem(null);

    }

    void timesetM() {
        nowTime();
        time_N.setText(today_date);
    }

    void onClick(View v) {
        Log.d("orderid", v.getId() + "");
        switch (v.getId()) {
            case R.id.manageA:
                intent = new Intent(this, ManageActivity.class);
                intent.putExtra("time", today_date);
                startActivity(intent);
                break;
            case R.id.salesA:
                intent = new Intent(this, SalesActivity.class);
                intent.putExtra("time", today_date);
                startActivity(intent);
                break;
            case R.id.settingA:
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("time", today_date);
                startActivity(intent);
                break;
            default:
                break;
        }//화면 변경
    }

    public void EonClick(View v) {
        if (v.getId() == R.id.item_edit_btn || v.getId() == R.id.edit_area) {
            if(edit_act)
                if(exitEdit())
                    return;
            mDragListView.setVisibility(View.VISIBLE);
            item_view.setVisibility(View.INVISIBLE);
            edit_act = true;
            DragListSetting();
            Toast.makeText(this, "편집모드가 설정되었습니다.", Toast.LENGTH_SHORT).show();

        } else {
            if(exitEdit())
                return;
        }
    }
    public boolean exitEdit(){
        if(edit_act) {
            mDragListView.setVisibility(View.INVISIBLE);
            item_view.setVisibility(View.VISIBLE);
            item_adapter.notifyDataSetChanged();
            edit_act = false;
            Toast.makeText(this, "편집모드가 해제되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    public void payClick(final View v) {
        if(exitEdit())
            return;
        View r_view = View.inflate(this, R.layout.paymethod, null);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(r_view);
        Button card = (Button) r_view.findViewById(R.id.card);
        Button cash = (Button) r_view.findViewById(R.id.cash);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v.getId() == R.id.prepay) {
                    payState = 1;
                    setCustomToast(OrderActivity.this, "카드 결제입니다");
//                    Toast.makeText(OrderActivity.this, "카드 결제입니다", Toast.LENGTH_SHORT).show();
                }
                if (v.getId() == R.id.afterpay) {
                    payState = 2;
                    setCustomToast(OrderActivity.this, "카드 결제입니다");

//                    Toast.makeText(OrderActivity.this, "현금 결제입니다", Toast.LENGTH_SHORT).show();
                }
                Log.d("test111", payState + "");
                dialog.dismiss();
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v.getId() == R.id.prepay) {
                    payState = 3;
                    setCustomToast(OrderActivity.this, "현금 결제입니다");

//                    Toast.makeText(OrderActivity.this, "카드 결제입니다", Toast.LENGTH_SHORT).show();
                }
                if (v.getId() == R.id.afterpay) {
                    payState = 4;
                    setCustomToast(OrderActivity.this, "현금 결제입니다");

//                    Toast.makeText(OrderActivity.this, "현금 결제입니다", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void nowTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatDate = sdfNow.format(date);
        today_date = formatDate.substring(0, 4) + "." + formatDate.substring(5, 7) + "." + formatDate.substring(8, 10) + "";
        today_time = formatDate.substring(11, 13) + ":" + formatDate.substring(14, 16);
    }//날짜와 시간 문자열로 받아옴

    String dateKey() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdfNow.format(date);
    }

    class sortWorks implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return (o1.getSeq() > o2.getSeq()) ? 1 : 0;
        }
    }


    public void setCustomToast(Context context, String msg) {

        TextView tvToastMsg = new TextView(context);
        tvToastMsg.setText(msg);
//        tvToastMsg.setBackgroundResource(Color.WHITE);
//        tvToastMsg.setTextColor(getResources().getColor(R.color.Key));
        tvToastMsg.setTextSize(16);
        final Toast toastMsg = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toastMsg.setView(tvToastMsg);
        toastMsg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toastMsg.cancel();
            }
        }, 1000);
    }

}
