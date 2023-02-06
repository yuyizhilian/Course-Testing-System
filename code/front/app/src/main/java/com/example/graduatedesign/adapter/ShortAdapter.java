package com.example.graduatedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.ProblemAndAnswer;

import java.util.List;

public class ShortAdapter extends BaseAdapter {
    private List<ProblemAndAnswer> problemAndAnswerList;
    private Context context;

    public ShortAdapter(Context context,List<ProblemAndAnswer> problemAndAnswerList){
        this.context=context;
        this.problemAndAnswerList=problemAndAnswerList;
    }

    @Override
    public int getCount() {
        return problemAndAnswerList.size();
    }

    @Override
    public Object getItem(int i) {
        return problemAndAnswerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProblemAndAnswer problemAndAnswer=problemAndAnswerList.get(i);
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_short,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.num.setText(problemAndAnswer.getProblem().getNumber()+"");
        holder.title.setText(problemAndAnswer.getProblem().getTitle());

        return view;
    }

    public class ViewHolder{

        private TextView num;
        private TextView title;
        public ViewHolder(View view) {
            num=view.findViewById(R.id.num);
            title=view.findViewById(R.id.title);
        }

    }
}
