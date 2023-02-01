package com.example.graduatedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.LessenTest;

import java.util.List;

public class WaitIssueFragmentAdapter extends BaseAdapter{

    private List<LessenTest> testList;

    private Context context;

    public WaitIssueFragmentAdapter(Context context, List<LessenTest> testList){
        this.context=context;
        this.testList=testList;
    }

    @Override
    public int getCount() {
        return testList.size();
    }

    @Override
    public Object getItem(int i) {
        return testList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LessenTest test=testList.get(i);
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.text_item,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        holder.name.setText(test.getTestName());
        String times=test.getTimes();
        String str="创建时间："+times;
        holder.time.setText(str);
        return view;
    }


    public class ViewHolder{

        private TextView name;
        private TextView time;
        public ViewHolder(View view) {
            name=view.findViewById(R.id.name);
            time=view.findViewById(R.id.times);
        }

    }

}
