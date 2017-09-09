package com.example.jisung.darimi;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by jeongjiseong on 2017. 8. 5..
 */

public class ItemAdapter extends DragItemAdapter<Item, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    Realm realm;
    int cate;

    ItemAdapter(ArrayList<Item> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
//        if(mItemList.get)
        String text = mItemList.get(position).getName();
        holder.name.setText(text);
        holder.price.setText(mItemList.get(position).getPrice());
        holder.img.setImageResource(mItemList.get(position).getImg());
        holder.itemView.setTag(mItemList.get(position));
        if(mItemList.get(position).isMark())
            holder.mark.setImageResource(R.drawable.item_marked);
    }


    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getSeq();
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView name,price;
        ImageView img,mark;



        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            name =(TextView)itemView.findViewById(R.id.item_name);
            price = (TextView)itemView.findViewById(R.id.item_price);
            img = (ImageView)itemView.findViewById(R.id.item_img);
            mark = (ImageView)itemView.findViewById(R.id.item_mark);

        }

        @Override
        public void onItemClicked(View view) {
            Log.d("test1", getPosition() + "");
            if (getPosition() == mItemList.size() - 1) {
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
                Button selcet_img = (Button) e_view.findViewById(R.id.change_img);
                Button comple = (Button) e_view.findViewById(R.id.edit_com);
                //대화상자 초기화


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
                        darimiDataCon.makeItem(realm,view.getContext(),eitem_name.getText().toString(),eitem_price.getText().toString(),R.drawable.images3,cate);
                        Toast.makeText(view.getContext(), "항목이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        notifyDataSetChanged();
                        //클릭시 아이템 변경
                            /*
                            데이터 베이스 전송
                             */

                    }
                });


                dialog.show();
                return;
            }
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
