package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.adapter.ChatAdapter;
import com.example.graduatedesign.adapter.MsgFragmentAdapter;
import com.example.graduatedesign.bean.AlreadyTest;
import com.example.graduatedesign.bean.Messages;
import com.example.graduatedesign.model.ChatModel;
import com.example.graduatedesign.model.ItemModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final int MESSAGE = 1;

    TextView title;
    EditText content;
    TextView send;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private SharedPreferences preferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_message);
        Intent intent=getIntent();
        String lessenID=intent.getStringExtra("lessenID");
        String lessenName=intent.getStringExtra("lessenName");
        title=findViewById(R.id.title);
        title.setText(lessenName);
        content=findViewById(R.id.content);
        send=findViewById(R.id.send);
        List<ItemModel> modelList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userID = preferences.getString("account", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path="http://mdzs.free.svipss.top/seeMessage?";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                    String data="lessenID="+ lessenID;
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                    //指定长度
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setDoOutput(true); //指定输出模式
                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                    int code = conn.getResponseCode();
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
                        List<ChatModel> chatModelList=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // JSON数组里面的具体-JSON对象
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String userID = jsonObject.optString("userID", null);
                            String user = jsonObject.optString("image", null);
                            String name = jsonObject.optString("name", null);
                            String times=jsonObject.optString("times",null);
                            String content=jsonObject.optString("content",null);
                            ChatModel chatModel=new ChatModel(userID,user,name,times,content);
                            chatModelList.add(chatModel);
                        }
                        Log.d("TAG", "run: "+chatModelList.size());
                        for(int i=0;i<chatModelList.size();i++){
                            if(chatModelList.get(i).getUserID().equals(userID)){
                                modelList.add(new ItemModel(ItemModel.CHAT_B,chatModelList.get(i)));
                            }else{
                                modelList.add(new ItemModel(ItemModel.CHAT_A,chatModelList.get(i)));
                            }
                        }
                        Message msg=new Message();
                        msg.what=MESSAGE;
                        msg.obj=modelList;
                        handler.sendMessage(msg);
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE:
                    List<ItemModel> itemModelList=(List<ItemModel>)msg.obj;
                    Log.d("TAG", "handleMessage: "+itemModelList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter = new ChatAdapter());
                    adapter.replaceAll((ArrayList<ItemModel>) itemModelList);
                    break;
            }
        }

    };
}