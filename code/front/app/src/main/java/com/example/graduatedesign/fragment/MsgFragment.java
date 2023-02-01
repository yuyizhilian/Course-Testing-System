package com.example.graduatedesign.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.graduatedesign.R;
import com.example.graduatedesign.adapter.MsgFragmentAdapter;
import com.example.graduatedesign.bean.Messages;

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

public class MsgFragment extends Fragment {
    private static final int MESSAGE = 1;
    private ListView lst;
    //判断用户点击了那个标签
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_msg_content,container,false);
        ButterKnife.bind(this,view);
        lst=view.findViewById(R.id.lst);
        initLessenData();
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE:
                    List<Messages> messages=(List<Messages>)msg.obj;
                    MsgFragmentAdapter adapter=new MsgFragmentAdapter(getContext(),messages);
                    lst.setAdapter(adapter);
                    break;
            }
        }

    };
    private void initLessenData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path="http://mdzs.free.svipss.top/seeMessage";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");              //设置POST方式获取数据
                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                    int code = conn.getResponseCode();         // 获取response状态，200表示成功获取资源，404表示资源不存在
                    if (code==200){
                        InputStream is=conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(is));
                        StringBuffer buffer=new StringBuffer();
                        String len;
                        while((len=br.readLine())!=null){
                            buffer.append(len);
                        }
                        JSONObject jsonObjectALL = new JSONObject(buffer.toString());
                        JSONArray jsonArray = jsonObjectALL.getJSONArray("data");
                        List<Messages> messagesList=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // JSON数组里面的具体-JSON对象
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String messageID=jsonObject.optString("messsageID",null);
                            String lessenName = jsonObject.optString("lessenName", null);
                            String image = jsonObject.optString("image", null);
                            String time=jsonObject.optString("times",null);
                            String mess=jsonObject.optString("msg",null);
                            String user=jsonObject.optString("user",null);
                            Messages message=new Messages(messageID,image,lessenName,user,mess,time);
                            messagesList.add(message);
                            Message msg=new Message();
                            msg.what=MESSAGE;
                            msg.obj=messagesList;
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
