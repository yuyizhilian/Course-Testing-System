package com.example.graduatedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.AlreadyTest;


import java.util.List;

public class AlreadyIssueFragmentAdapter extends BaseAdapter {

    private List<AlreadyTest> alreadyTestList;

    private Context context;

    public AlreadyIssueFragmentAdapter(Context context, List<AlreadyTest> alreadyTestList){
        this.context=context;
        this.alreadyTestList=alreadyTestList;
    }

    @Override
    public int getCount() {
        return alreadyTestList.size();
    }

    @Override
    public Object getItem(int i) {
        return alreadyTestList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlreadyTest test=alreadyTestList.get(i);
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.already_test_item,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        holder.name.setText(test.getName());
        String full="满分："+test.getFulls();
        holder.grade.setText(full);
        String number="重做次数："+test.getNumbers();
        holder.number.setText(number);
        String start="开始时间："+test.getStarts();
        holder.start.setText(start);
        String end="结束时间："+test.getEnds();
        holder.end.setText(end);
        return view;
    }


    public class ViewHolder{

        private TextView name;
        private TextView grade;
        private TextView number;
        private TextView start;
        private TextView end;

        public ViewHolder(View view) {
            name=view.findViewById(R.id.name);
            grade=view.findViewById(R.id.grade);
            number=view.findViewById(R.id.number);
            start=view.findViewById(R.id.start);
            end=view.findViewById(R.id.end);
        }

    }
}
