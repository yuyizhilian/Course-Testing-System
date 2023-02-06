package com.example.graduatedesign.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatedesign.R;
import com.example.graduatedesign.model.ChatModel;
import com.example.graduatedesign.model.ItemModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseAdapter> {

    private ArrayList<ItemModel> dataList = new ArrayList<>();

    private RequestOptions requestOptions = com.bumptech.glide.request.RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public void replaceAll(ArrayList<ItemModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ItemModel> list) {
        if (dataList != null && list != null) {
            dataList.addAll(list);
            notifyItemRangeChanged(dataList.size(),list.size());
        }

    }

    @Override
    public BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.CHAT_A:
                return new ChatAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_other, parent, false));
            case ItemModel.CHAT_B:
                return new ChatBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_my, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.BaseAdapter holder, int position) {
        holder.setData(dataList.get(position).object);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatAViewHolder extends BaseAdapter {
        private ShapeableImageView user;
        private TextView name;
        private TextView content;
        private TextView times;

        public ChatAViewHolder(View view) {
            super(view);
            user = view.findViewById(R.id.user);
            name = view.findViewById(R.id.name);
            content = view.findViewById(R.id.content);
            times = view.findViewById(R.id.times);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatModel model = (ChatModel) object;
            Log.d("TAG", "setData: "+model.getUser());
            Glide.with(itemView.getContext()).load(model.getUser()).apply(requestOptions).into(user);
            name.setText(model.getName());
            content.setText(model.getContent());
            times.setText(model.getTimes());

        }
    }

    private class ChatBViewHolder extends BaseAdapter {
        private ShapeableImageView user;
        private TextView name;
        private TextView content;
        private TextView times;

        public ChatBViewHolder(View view) {
            super(view);
            user = view.findViewById(R.id.user);
            name = view.findViewById(R.id.name);
            content = view.findViewById(R.id.content);
            times = view.findViewById(R.id.times);

        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatModel model = (ChatModel) object;
            Picasso.with(itemView.getContext()).load(model.getUser()).placeholder(R.mipmap.ic_launcher).into(user);
            name.setText(model.getName());
            content.setText(model.getContent());
            times.setText(model.getTimes());
        }
    }
}
