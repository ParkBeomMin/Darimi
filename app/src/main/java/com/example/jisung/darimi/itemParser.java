package com.example.jisung.darimi;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by jisung on 2017-09-09.
 */

public class itemParser {
    public static ArrayList<item_basic> parserString(String data){
        StringTokenizer st = new StringTokenizer(data,"/");
        ArrayList<item_basic> list = new ArrayList<item_basic>();
        while(st.hasMoreTokens()){
            item_basic item = new item_basic();
            item.setName(st.nextToken());
            item.setPrice(st.nextToken());
            item.setNum(st.nextToken());
            list.add(item);
        }
        return list;
    }
    public static String parserList(ArrayList<Items> list){
        String data="";
        for(int i=0;i<list.size();i++){
            if(i!=0)
                data+="/";
            data+=list.get(i).getItem().getName()+"/";
            data+=list.get(i).getItem().getPrice()+"/";
            data+=list.get(i).getItem_num();
        }
        return data;
    }
}
