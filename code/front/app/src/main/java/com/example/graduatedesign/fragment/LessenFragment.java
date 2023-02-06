package com.example.graduatedesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.graduatedesign.R;
import com.example.graduatedesign.activity.LessenDetailsActivity;
import com.example.graduatedesign.adapter.LessenFragmentAdapter;
import com.example.graduatedesign.bean.Lessen;
import com.example.graduatedesign.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class LessenFragment extends Fragment {
    private static final int TEST_USER_SELECT = 1;
    private ListView lst;
    //判断用户点击了那个标签
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lessen_content,container,false);
        ButterKnife.bind(this,view);
        lst=view.findViewById(R.id.list);
        initLessenData();
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TEST_USER_SELECT:
                    List<Lessen> lessens=(List<Lessen>)msg.obj;
                    LessenFragmentAdapter adapter=new LessenFragmentAdapter(getContext(),lessens);
                    lst.setAdapter(adapter);
                    lst.setOnItemClickListener((parent, view, position, id) -> {
                        String lessenName=lessens.get(position).getLessenName();
                        String lessenID=lessens.get(position).getLessenID();
                        SPUtils.putString("lessenID",lessenID,getContext());
                        Intent intent=new Intent(getContext(), LessenDetailsActivity.class);
                        intent.putExtra("lessenName",lessenName);
                        intent.putExtra("lessenID",lessenID);
                        startActivity(intent);
                    });
                    break;
            }
        }

    };
    private void initLessenData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path="http://mdzs.free.svipss.top/seeLessen";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");              //设置POST方式获取数据
                    conn.setConnectTimeout(10000);              //设置连接超时时间5秒
                    int code = conn.getResponseCode();         // 获取response状态，200表示成功获取资源，404表示资源不存在
                    if (code==200){
                        InputStream is=conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(is));
                        StringBuffer buffer=new StringBuffer();
                        String len=null;
                        while((len=br.readLine())!=null){
                            buffer.append(len);
                        }
                        JSONObject jsonObjectALL = new JSONObject(buffer.toString());
                        JSONArray jsonArray = jsonObjectALL.getJSONArray("data");
                        List<Lessen> lessenList=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // JSON数组里面的具体-JSON对象
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String lessenID=jsonObject.optString("lessenID",null);
                            String lessenName = jsonObject.optString("lessenName", null);
                            String image = jsonObject.optString("image", null);
                            Lessen lessen=new Lessen(lessenID,lessenName,image);
                            lessenList.add(lessen);
                            Message msg=new Message();
                            msg.what=TEST_USER_SELECT;
                            msg.obj=lessenList;
                            handler.sendMessage(msg);
                        }

                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
