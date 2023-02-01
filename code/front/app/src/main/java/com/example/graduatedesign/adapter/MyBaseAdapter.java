package com.example.graduatedesign.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建自己的适配器
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas=new ArrayList<>();
    protected Context mContext;
    public MyBaseAdapter(Context context){
        this.mContext=context;
    }

    //获取数据的大小
    @Override
    public int getCount(){
        return mDatas.size();
    }

    //获取数据的对象
    @Override
    public Object getItem(int position){
        return mDatas.get(position);
    }

    //获取对象的ID
    @Override
    public long getItemId(int position){
        return position;
    }

    //向数据中添加数据
    public void addData(List<T> datas){
        if(mDatas != null && datas != null &&!datas.isEmpty()){
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    //更改数据
    public void notifyAdapter(List<T> datas){
        this.mDatas=datas;
        notifyDataSetChanged();
    }

    //设置数据
    public void setDatas(List<T> datas){
        this.mDatas=datas;
    }
}

