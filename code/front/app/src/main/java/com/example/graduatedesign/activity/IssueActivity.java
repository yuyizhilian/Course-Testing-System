package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.LessenTest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IssueActivity extends AppCompatActivity {

    private static final int INTENT= 1;
    TimePickerView pvTime;

    TextView starts;
    String start;  //接收开始时间
    TextView ends;
    String end; //接收结束时间

    Button publish;

    EditText full;
    EditText pass;
    EditText times;

    Editable fulls;  //接收满分
    Editable grade;   //接收及格分
    Editable num;   //接收重做次数；

    Switch paste;    //允许粘贴答案案
    Switch highest;     //多次重做取最高分
    Switch mark;      //忽略符号
    Switch gain;      //多选题未选全给一半的分数
    Switch random;   //随机出题
    Switch cases;    //忽略符号

    int pastes;
    int highests;
    int marks;
    int gains;
    int randoms;
    int  r_cases;

    String testID;
    String lessenID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_issue);
        Intent intent=getIntent();
        testID=intent.getStringExtra("testID");
        System.out.println("66666666666666"+testID);
        lessenID=intent.getStringExtra("lessenID");
        starts=findViewById(R.id.starts);
        ends=findViewById(R.id.ends);
        publish=findViewById(R.id.publish);
        full=findViewById(R.id.full);
        pass=findViewById(R.id.pass);
        times=findViewById(R.id.times);
        paste=findViewById(R.id.paste);
        highest=findViewById(R.id.highest);
        mark=findViewById(R.id.mark);
        gain=findViewById(R.id.gain);
        random=findViewById(R.id.random);
        cases=findViewById(R.id.cases);
        starts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击组件的点击事件
                if (pvTime!=null){
                    pvTime.show(starts);
                }
            }
        });
        paste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pastes=1;
                }else{
                    pastes=0;
                }
            }
        });
        highest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    highests=1;
                }else{
                    highests=0;
                }
            }
        });
        mark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    marks=1;
                }else{
                    marks=0;
                }
            }
        });
        gain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    gains=1;
                }else{
                    gains=0;
                }
            }
        });
        random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    randoms=1;
                }else{
                    randoms=0;
                }
            }
        });
        cases.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    r_cases=1;
                }else{
                    r_cases=0;
                }
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start= (String) starts.getText();
                Log.d("111111111111111111111", start);
                end= (String) ends.getText();
                Log.d("22222222222222222", end);
                fulls=full.getText();
                grade=pass.getText();
                num=times.getText();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String path="http://mdzs.free.svipss.top/storageIssue?";
                            URL url = new URL(path);
                            //打开httpurlconnection
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");              //设置POST方式获取数据
                            conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                            //将数据进行编码,然后会自动的将该数据放到post中传到后台
                            String data="starts="+ start+"&ends="+end+"&pass="+grade+"&full="+fulls+"&numbers="+num+"&highest="+highests+"&paste="+pastes+"&case="+r_cases+"&mark="+marks+"&gain="+gains+"&testID="+testID+"&lessenID="+lessenID;
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                            //指定长度
                            conn.setRequestProperty("Charset", "UTF-8");
                            conn.setRequestProperty("connection", "keep-alive");
                            conn.setDoOutput(true); //指定输出模式
                            conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                            int code = conn.getResponseCode();
                            if (code==200){

                            }
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Message msg = new Message();
                msg.what = INTENT;
                handler.sendMessage(msg);

            }
        });
        ends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击组件的点击事件
                if (pvTime!=null){
                    pvTime.show(ends);
                }
            }
        });

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTimes(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(16)//字号
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();




    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String user;
            switch (msg.what){

                case INTENT :
                    Intent intent=new Intent(IssueActivity.this,LessenTestActivity.class);
                    startActivity(intent);
                    break;

            }
        }

    };
    private String getTimes(Date date) {//年月日时分秒格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}