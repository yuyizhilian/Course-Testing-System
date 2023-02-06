package com.example.graduatedesign.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatedesign.R;
import com.example.graduatedesign.activity.MyActivity;
import com.example.graduatedesign.utils.SPUtils;
import com.google.android.material.imageview.ShapeableImageView;
public class MeFragment extends Fragment {
    ShapeableImageView imageView;
    LinearLayout head;

    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me_content,container,false);
        head=view.findViewById(R.id.head);
        imageView=view.findViewById(R.id.iv_head);
        //取出缓存
        String imageUrl = SPUtils.getString("imageUrl",null,getContext());
        if(imageUrl != null){
            Glide.with(this).load(imageUrl).apply(requestOptions).into(imageView);
        }
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MyActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
