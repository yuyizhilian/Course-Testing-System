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
import com.example.graduatedesign.adapter.AlreadyIssueFragmentAdapter;
import com.example.graduatedesign.bean.AlreadyTest;
import com.example.graduatedesign.bean.LessenTest;
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

public class AlreadyIssueFragment extends Fragment {

    private static final int MESSAGE = 1;

    private ListView lst;

    private String lessenID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_already_issue,container,false);
        ButterKnife.bind(this,view);
        lst=view.findViewById(R.id.lst);
        lessenID= SPUtils.getString("lessenID",null,getContext());
        initLessenData();
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String user;
            switch (msg.what){
                case MESSAGE:
                    List<AlreadyTest> testList=(List<AlreadyTest>)msg.obj;
                    AlreadyIssueFragmentAdapter adapter=new AlreadyIssueFragmentAdapter(getContext(),testList);
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
                    String path="http://mdzs.free.svipss.top/seeAlreadyTestItem?";
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
                        List<AlreadyTest> testList=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // JSON数组里面的具体-JSON对象
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String publishID=jsonObject.optString("publishID",null);
                            String testID=jsonObject.optString("testID",null);
                            String testName = jsonObject.optString("publishName", null);
                            int grade = Integer.parseInt(jsonObject.optString("fulls", null));
                            int number = Integer.parseInt(jsonObject.optString("numbers", null));
                            String start=jsonObject.optString("starts",null);
                            String end=jsonObject.optString("ends",null);
                            AlreadyTest test=new AlreadyTest(publishID,testID,testName,grade,number,start,end);
                            testList.add(test);
                            Message msg=new Message();
                            msg.what=MESSAGE;
                            msg.obj=testList;
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
