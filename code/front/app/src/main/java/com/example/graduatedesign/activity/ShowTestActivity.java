package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.adapter.FillAdapter;
import com.example.graduatedesign.adapter.JudgeAdapter;
import com.example.graduatedesign.adapter.MultipleAdapter;
import com.example.graduatedesign.adapter.ShortAdapter;
import com.example.graduatedesign.adapter.SingleAdapter;
import com.example.graduatedesign.bean.Answer;
import com.example.graduatedesign.bean.Lessen;
import com.example.graduatedesign.bean.LessenTest;
import com.example.graduatedesign.bean.Problem;
import com.example.graduatedesign.bean.ProblemAndAnswer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowTestActivity extends AppCompatActivity {

    private static final int NAME = 1;
    private static final int PROBLEM = 2;
    private static final int SINGLE = 3;
    private static final int MULTIPLE = 4;
    private static final int FILL = 5;
    private static final int JUDGE = 6;
    private static final int SHORT = 7;

    private ListView single_list;
    private ListView multiple_list;
    private ListView fill_list;
    private ListView judge_list;
    private ListView short_list;

    TextView name;
    String testID;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_show_test);
        name=findViewById(R.id.name);
        single_list=findViewById(R.id.single_list);
        multiple_list=findViewById(R.id.multiple_list);
        fill_list=findViewById(R.id.fill_list);
        judge_list=findViewById(R.id.judge_list);
        short_list=findViewById(R.id.short_list);
        Intent intent=getIntent();
        testID=intent.getStringExtra("testID");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path="http://mdzs.free.svipss.top/seeTestName?";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                    String data="testID="+ testID;
                    Log.d("555555", data);
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
                        JSONObject jsonObject = new JSONObject(buffer.toString());
                        String testName = jsonObject.optString("data", null);
                        Message msg=new Message();
                        msg.what=NAME;
                        msg.obj=testName;
                        handler.sendMessage(msg);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String path="http://mdzs.free.svipss.top/seeProblem?";
                                    URL url = new URL(path);
                                    //打开httpurlconnection
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                    String data="testID="+ testID;
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
                                        List<ProblemAndAnswer> singleList=new ArrayList<>();
                                        List<ProblemAndAnswer> multipleList=new ArrayList<>();
                                        List<ProblemAndAnswer> judgeList=new ArrayList<>();
                                        List<ProblemAndAnswer> fillList=new ArrayList<>();
                                        List<ProblemAndAnswer> shortList=new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String problemID=jsonObject.optString("problemID",null);
                                            String testID = jsonObject.optString("testID", null);
                                            int number = Integer.parseInt(jsonObject.optString("numbers", null));
                                            int type= Integer.parseInt(jsonObject.optString("types",null));
                                            String title=jsonObject.optString("titles",null);
                                            String right=jsonObject.optString("rights",null);
                                            Problem problem=new Problem(problemID,testID,number,type,title,right);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        String path="http://mdzs.free.svipss.top/seeAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data="problemID="+ problemID;
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
                                                            List<Answer> answerList=new ArrayList<>();
                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                // JSON数组里面的具体-JSON对象
                                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                                String answerID=jsonObject.optString("answerID",null);
                                                                String problemID = jsonObject.optString("problemID", null);
                                                                String tag=jsonObject.optString("tag",null);
                                                                String content=jsonObject.optString("content",null);
                                                                Answer answer=new Answer(answerID,problemID,tag,content);
                                                                answerList.add(answer);
                                                            }
                                                            ProblemAndAnswer problemAndAnswer=new ProblemAndAnswer(problem,answerList);
                                                            if(type==0){
                                                                singleList.add(problemAndAnswer);
                                                                Message msg=new Message();
                                                                msg.what=SINGLE;
                                                                msg.obj=singleList;
                                                                handler.sendMessage(msg);
                                                            }
                                                            if(type==1){
                                                                multipleList.add(problemAndAnswer);
                                                                Message msg=new Message();
                                                                msg.what=MULTIPLE;
                                                                msg.obj=multipleList;
                                                                handler.sendMessage(msg);
                                                            }
                                                            if(type==2){
                                                                fillList.add(problemAndAnswer);
                                                                Message msg=new Message();
                                                                msg.what=FILL;
                                                                msg.obj=fillList;
                                                                handler.sendMessage(msg);
                                                            }
                                                            if(type==3){
                                                                judgeList.add(problemAndAnswer);
                                                                Message msg=new Message();
                                                                msg.what=JUDGE;
                                                                msg.obj=judgeList;
                                                                handler.sendMessage(msg);
                                                            }
                                                            if(type==4){
                                                                shortList.add(problemAndAnswer);
                                                                Message msg=new Message();
                                                                msg.what=SHORT;
                                                                msg.obj=shortList;
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
                                    conn.disconnect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

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
                case NAME:
                    String testName= (String) msg.obj;
                    name.setText(testName);
                    break;
                case SINGLE:
                    List<ProblemAndAnswer> list_single= (List<ProblemAndAnswer>) msg.obj;
                    SingleAdapter adapter_single=new SingleAdapter(ShowTestActivity.this,list_single);
                    single_list.setAdapter(adapter_single);
                    break;
                case MULTIPLE:
                    List<ProblemAndAnswer> list_multiple= (List<ProblemAndAnswer>) msg.obj;
                    MultipleAdapter adapter_multiple=new MultipleAdapter(ShowTestActivity.this,list_multiple);
                    multiple_list.setAdapter(adapter_multiple);
                    break;
                case FILL:
                    List<ProblemAndAnswer> list_fill= (List<ProblemAndAnswer>) msg.obj;
                    FillAdapter adapter_fill=new FillAdapter(ShowTestActivity.this,list_fill);
                    fill_list.setAdapter(adapter_fill);
                    break;
                case JUDGE:
                    List<ProblemAndAnswer> list_judge= (List<ProblemAndAnswer>) msg.obj;
                    JudgeAdapter adapter_judge=new JudgeAdapter(ShowTestActivity.this,list_judge);
                    judge_list.setAdapter(adapter_judge);
                    break;
                case SHORT:
                    List<ProblemAndAnswer> list_short= (List<ProblemAndAnswer>) msg.obj;
                    ShortAdapter adapter_short=new ShortAdapter(ShowTestActivity.this,list_short);
                    short_list.setAdapter(adapter_short);
                    break;

            }
        }

    };
}