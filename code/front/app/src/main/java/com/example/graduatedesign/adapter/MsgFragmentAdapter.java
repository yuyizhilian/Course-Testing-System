package com.example.graduatedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.Messages;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MsgFragmentAdapter extends BaseAdapter {
    List<Messages> messages;
    private Context context;

    public MsgFragmentAdapter(Context context, List<Messages> messages){
        this.context=context;
        this.messages=messages;
    }

    RoundedCorners roundedCorners= new RoundedCorners(6);
    private RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false);

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Messages message=messages.get(i);
        ViewHolderMsg holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.message_review,null,false);
            holder=new ViewHolderMsg(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolderMsg) view.getTag();
        }
        holder.name.setText(message.getLessenName());
        String str=message.getUser()+":"+message.getMsg();
        holder.content.setText(str);
        holder.time.setText(message.getTime());
        Glide.with(context).load(message.getImage()).apply(requestOptions).into(holder.img);
        return view;
    }
    public class ViewHolderMsg{

        private ShapeableImageView img;
        private TextView name;
        private TextView time;
        private TextView content;

        public ViewHolderMsg(View view) {
            img=view.findViewById(R.id.img);
            name=view.findViewById(R.id.name);
            time=view.findViewById(R.id.time);
            content=view.findViewById(R.id.content);
        }

    }
}
