package com.example.graduatedesign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.ProblemAndAnswer;

import java.util.List;
import java.util.Objects;

public class SingleAdapter extends BaseAdapter {

    private List<ProblemAndAnswer> problemAndAnswerList;
    private Context context;

    public SingleAdapter(Context context,List<ProblemAndAnswer> problemAndAnswerList){
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProblemAndAnswer problemAndAnswer=problemAndAnswerList.get(i);
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_single,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.num.setText(problemAndAnswer.getProblem().getNumber()+"");
        holder.title.setText(problemAndAnswer.getProblem().getTitle()+"【单选题】");
        holder.aa.setText(problemAndAnswer.getAnswer().get(0).getContent());
        holder.bb.setText(problemAndAnswer.getAnswer().get(1).getContent());
        holder.cc.setText(problemAndAnswer.getAnswer().get(2).getContent());
        holder.dd.setText(problemAndAnswer.getAnswer().get(3).getContent());

        return view;
    }

    public class ViewHolder{

        private TextView num;
        private TextView title;
        private TextView aa;
        private TextView bb;
        private TextView cc;
        private TextView dd;
        public ViewHolder(View view) {
            num=view.findViewById(R.id.num);
            title=view.findViewById(R.id.title);
            aa=view.findViewById(R.id.aa);
            bb=view.findViewById(R.id.bb);
            cc=view.findViewById(R.id.cc);
            dd=view.findViewById(R.id.dd);
        }

    }
}
