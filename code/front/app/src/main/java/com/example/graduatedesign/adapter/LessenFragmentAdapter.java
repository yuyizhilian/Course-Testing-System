package com.example.graduatedesign.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.Lessen;

import java.util.List;

public class LessenFragmentAdapter extends BaseAdapter{
    List<Lessen> lessens;
    private Context context;
    public LessenFragmentAdapter(Context context, List<Lessen> lessens){
        this.context=context;
        this.lessens=lessens;
    }

    RoundedCorners roundedCorners= new RoundedCorners(6);
    private RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false);

    @Override
    public int getCount() {
        return lessens.size();
    }

    @Override
    public Object getItem(int i) {
        return lessens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Lessen lessen=lessens.get(i);
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.lessen_review,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.name.setText(lessen.getLessenName());
        Glide.with(context).load(lessen.getImage()).apply(requestOptions).into(holder.img);
        return view;
    }
    public class ViewHolder{

        private ImageView img;
        private TextView name;

        public ViewHolder(View view) {
            img=view.findViewById(R.id.img);
            name=view.findViewById(R.id.name);
        }

    }

}
