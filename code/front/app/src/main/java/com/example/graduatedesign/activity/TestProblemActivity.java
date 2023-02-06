package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.Answer;
import com.example.graduatedesign.bean.Problem;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestProblemActivity extends AppCompatActivity {

    private static final int TEST_USER_SELECT = 1;
    private static final int MESS1 = 2;
    private static final int MESS2= 3;
    private static final int MESS3= 4;
    private static final int MESS4= 5;
    private static final int MESS5= 6;

    private static final int INTENT= 7;


    //private final int[] items = new int[]{R.string.single,R.string.multiple,R.string.judge,R.string.fill,R.string.shorts};
    private String[] items=new String[]{"单选题","多选题","判断题","填空题","简答题"};
    LinearLayout single;
    LinearLayout multiple;
    LinearLayout fill;
    LinearLayout judge;

    RadioGroup group1;
    RadioButton button1;
    String selectText1;

    RadioGroup group2;
    RadioButton button2;
    String selectText2;

    CheckBox A;
    CheckBox B;
    CheckBox C;
    CheckBox D;
    EditText shorts;  //简答题的答案
    Button next;   //创建下一题
    Button finish; //完成创建
    EditText name;  //测试的名称
    String titleName;
    TextView number;  //测试的题号
    int num;
    EditText title;  //题目
    String title_name;  //题目的内容

    EditText aa;  //单选题答案A的内容
    EditText bb;  //单选题答案B的内容
    EditText cc;  //单选题答案C的内容
    EditText dd;  //单选题答案D的内容

    EditText AA;  //多选题答案D的内容
    EditText BB;  //多选题答案D的内容
    EditText CC;  //多选题答案D的内容
    EditText DD;  //多选题答案D的内容

    EditText one;  //填空题第一空的内容
    EditText two;  //填空题第二空的内容
    EditText three;  //填空题第三空的内容
    EditText four;  //填空题第四空的内容

    String testID;

    Boolean temp=true;  //判断是否是第一次

    String right="";  //多选题的正确答案

    Spinner ddlCity;

    private SharedPreferences preferences;
    List<Answer> list_single=new ArrayList<>();  //单选题答案集合
    List<Answer> list_multiple=new ArrayList<>();  //多选题答案集合
    List<Answer> list_fill=new ArrayList<>();  //填空题答案集合
    List<String> list_shorts=new ArrayList<>();  //简答题答案

    String lessenID;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_test_problem);
        init();
        Intent intent = getIntent();
        lessenID=intent.getStringExtra("lessenID");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userID = preferences.getString("account", "");
        ArrayAdapter<String> source = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddlCity = (Spinner) findViewById(R.id.ddlCity);
        ddlCity.setAdapter(source);
        ddlCity.setSelection(0, true);
        ddlCity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String data = (String)ddlCity.getItemAtPosition(i);
                switch (i) {
                    case 0:
                        single.setVisibility(View.VISIBLE);
                        multiple.setVisibility(View.GONE);
                        judge.setVisibility(View.GONE);
                        fill.setVisibility(View.GONE);
                        shorts.setVisibility(View.GONE);
                        break;
                    case 1:
                        multiple.setVisibility(View.VISIBLE);
                        single.setVisibility(View.GONE);
                        judge.setVisibility(View.GONE);
                        fill.setVisibility(View.GONE);
                        shorts.setVisibility(View.GONE);
                        break;
                    case 2:
                        judge.setVisibility(View.VISIBLE);
                        single.setVisibility(View.GONE);
                        multiple.setVisibility(View.GONE);
                        fill.setVisibility(View.GONE);
                        shorts.setVisibility(View.GONE);
                        break;
                    case 3:
                        fill.setVisibility(View.VISIBLE);
                        single.setVisibility(View.GONE);
                        multiple.setVisibility(View.GONE);
                        judge.setVisibility(View.GONE);
                        shorts.setVisibility(View.GONE);
                        break;
                    case 4:
                        shorts.setVisibility(View.VISIBLE);
                        single.setVisibility(View.GONE);
                        multiple.setVisibility(View.GONE);
                        judge.setVisibility(View.GONE);
                        fill.setVisibility(View.GONE);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button1 = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                selectText1 = button1.getText().toString();
            }
        });
        group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button2 = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                selectText2 = button2.getText().toString();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleName = String.valueOf(name.getText());
                if (temp) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://mdzs.free.svipss.top/storageTest?";
                                URL url = new URL(path);
                                //打开httpurlconnection
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                String data = "testName=" + titleName + "&userID=" + userID;
                                conn.setRequestProperty("Charset", "UTF-8");
                                conn.setRequestProperty("connection", "keep-alive");
                                conn.setDoOutput(true); //指定输出模式
                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                int code = conn.getResponseCode();
                                if (code == 200) {
                                    InputStream is = conn.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    StringBuffer buffer = new StringBuffer();
                                    String len = null;
                                    while ((len = br.readLine()) != null) {
                                        buffer.append(len);
                                    }
                                    JSONObject jsonObject = new JSONObject(buffer.toString());
                                    testID = jsonObject.optString("data", null);
                                    temp = false;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String path = "http://mdzs.free.svipss.top/storageUnreleasedTest?";
                                                URL url = new URL(path);
                                                //打开httpurlconnection
                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                String data = "lessenID=" + lessenID + "&testID=" + testID;
                                                conn.setRequestProperty("Charset", "UTF-8");
                                                conn.setRequestProperty("connection", "keep-alive");
                                                conn.setDoOutput(true); //指定输出模式
                                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                int code = conn.getResponseCode();
                                                if (code == 200) {

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
                } else {
                    String str = (String) number.getText();
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(str);
                    String result = m.replaceAll("").trim();
                    title_name = String.valueOf(title.getText());
                    num = Integer.valueOf(result);
                    Message msg = new Message();
                    msg.what = TEST_USER_SELECT;
                    msg.obj = num;
                    handler.sendMessage(msg);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://mdzs.free.svipss.top/storageProblem?";
                                URL url = new URL(path);
                                //打开httpurlconnection
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                String data = "testID=" + testID + "&number=" + num + "&title=" + title_name;
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                                //指定长度
                                conn.setRequestProperty("Charset", "UTF-8");
                                conn.setRequestProperty("connection", "keep-alive");
                                conn.setDoOutput(true); //指定输出模式
                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                int code = conn.getResponseCode();
                                if (code == 200) {
                                    InputStream is = conn.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    StringBuffer buffer = new StringBuffer();
                                    String len = null;
                                    while ((len = br.readLine()) != null) {
                                        buffer.append(len);
                                    }
                                    JSONObject jsonObject = new JSONObject(buffer.toString());
                                    String problemID = jsonObject.optString("data", null);
                                    if (single.getVisibility() == View.VISIBLE) { //题型为单选题
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID + "&right=" + selectText1+"&type="+0;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "A", aa.getText().toString());
                                        list_single.add(answer1);
                                        Answer answer2 = new Answer(problemID, "B", bb.getText().toString());
                                        list_single.add(answer2);
                                        Answer answer3 = new Answer(problemID, "C", cc.getText().toString());
                                        list_single.add(answer3);
                                        Answer answer4 = new Answer(problemID, "D", dd.getText().toString());
                                        list_single.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_single.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_single.get(i).getProblemID() + "&tag=" + list_single.get(i).getTag() + "&content=" + list_single.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = MESS1;
                                        handler.sendMessage(msg);
                                    }
                                    if (multiple.getVisibility() == View.VISIBLE) { //题型为多选题
                                        if(A.isChecked()) {
                                            right += "A";
                                        }
                                        if(B.isChecked()){
                                            right += "B";
                                        }
                                        if(C.isChecked()){
                                            right += "C";
                                        }
                                        if(D.isChecked()){
                                            right += "D";
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID + "&right=" + right+"&type="+1;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "A", AA.getText().toString());
                                        list_multiple.add(answer1);
                                        Answer answer2 = new Answer(problemID, "B", BB.getText().toString());
                                        list_multiple.add(answer2);
                                        Answer answer3 = new Answer(problemID, "C", CC.getText().toString());
                                        list_multiple.add(answer3);
                                        Answer answer4 = new Answer(problemID, "D", DD.getText().toString());
                                        list_multiple.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_multiple.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_multiple.get(i).getProblemID() + "&tag=" + list_multiple.get(i).getTag() + "&content=" + list_multiple.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = MESS2;
                                        handler.sendMessage(msg);
                                    }
                                    if (fill.getVisibility() == View.VISIBLE) { //题型为填空题
                                        String right=one.getText().toString()+","+two.getText().toString()+","+three.getText().toString()+","+four.getText().toString();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID + "&right=" + right+"&type="+2;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "1", one.getText().toString());
                                        list_fill.add(answer1);
                                        Answer answer2 = new Answer(problemID, "2", two.getText().toString());
                                        list_fill.add(answer2);
                                        Answer answer3 = new Answer(problemID, "3", three.getText().toString());
                                        list_fill.add(answer3);
                                        Answer answer4 = new Answer(problemID, "4", four.getText().toString());
                                        list_fill.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_fill.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_fill.get(i).getProblemID() + "&tag=" + list_fill.get(i).getTag() + "&content=" + list_fill.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = MESS3;
                                        handler.sendMessage(msg);
                                    }
                                    if (judge.getVisibility() == View.VISIBLE) { //题型为判断题
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID + "&right=" + selectText2+"&type="+3;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = MESS4;
                                        handler.sendMessage(msg);
                                    }
                                    if (shorts.getVisibility() == View.VISIBLE) { //题型为简答题
                                        String right=shorts.getText().toString();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID + "&right=" + right+"&type="+4;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = MESS5;
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
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleName = String.valueOf(name.getText());
                if (temp) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://mdzs.free.svipss.top/storageTest?";
                                URL url = new URL(path);
                                //打开httpurlconnection
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                String data = "testName=" + titleName + "&userID=" + userID;
                                conn.setRequestProperty("Charset", "UTF-8");
                                conn.setRequestProperty("connection", "keep-alive");
                                conn.setDoOutput(true); //指定输出模式
                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                int code = conn.getResponseCode();
                                if (code == 200) {
                                    InputStream is = conn.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    StringBuffer buffer = new StringBuffer();
                                    String len = null;
                                    while ((len = br.readLine()) != null) {
                                        buffer.append(len);
                                    }
                                    JSONObject jsonObject = new JSONObject(buffer.toString());
                                    testID = jsonObject.optString("data", null);
                                    temp = false;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String path = "http://mdzs.free.svipss.top/storageUnreleasedTest?";
                                                URL url = new URL(path);
                                                //打开httpurlconnection
                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                String data = "lessenID=" + lessenID + "&testID=" + testID;
                                                conn.setRequestProperty("Charset", "UTF-8");
                                                conn.setRequestProperty("connection", "keep-alive");
                                                conn.setDoOutput(true); //指定输出模式
                                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                int code = conn.getResponseCode();
                                                if (code == 200) {

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
                } else {
                    String str = (String) number.getText();
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(str);
                    String result = m.replaceAll("").trim();
                    title_name = String.valueOf(title.getText());
                    num = Integer.valueOf(result);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://mdzs.free.svipss.top/storageProblem?";
                                URL url = new URL(path);
                                //打开httpurlconnection
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");              //设置POST方式获取数据
                                conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                String data = "testID=" + testID + "&number=" + num + "&title=" + title_name;
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                                //指定长度
                                conn.setRequestProperty("Charset", "UTF-8");
                                conn.setRequestProperty("connection", "keep-alive");
                                conn.setDoOutput(true); //指定输出模式
                                conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                int code = conn.getResponseCode();
                                if (code == 200) {
                                    InputStream is = conn.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    StringBuffer buffer = new StringBuffer();
                                    String len = null;
                                    while ((len = br.readLine()) != null) {
                                        buffer.append(len);
                                    }
                                    JSONObject jsonObject = new JSONObject(buffer.toString());
                                    String problemID = jsonObject.optString("data", null);
                                    if (single.getVisibility() == View.VISIBLE) { //题型为单选题
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {

                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID  + "&right=" + selectText1+"&type="+0;
                                                    Log.d("1111111111111111", data);
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "A", aa.getText().toString());
                                        list_single.add(answer1);
                                        Answer answer2 = new Answer(problemID, "B", bb.getText().toString());
                                        list_single.add(answer2);
                                        Answer answer3 = new Answer(problemID, "C", cc.getText().toString());
                                        list_single.add(answer3);
                                        Answer answer4 = new Answer(problemID, "D", dd.getText().toString());
                                        list_single.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_single.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_single.get(i).getProblemID() + "&tag=" + list_single.get(i).getTag() + "&content=" + list_single.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = INTENT;
                                        handler.sendMessage(msg);
                                    }
                                    if (multiple.getVisibility() == View.VISIBLE) { //题型为多选题
                                        if(A.isChecked()) {
                                            right += "A";
                                        }
                                        if(B.isChecked()){
                                            right += "B";
                                        }
                                        if(C.isChecked()){
                                            right += "C";
                                        }
                                        if(D.isChecked()){
                                            right += "D";
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID  + "&right=" + right+"&type="+ 1;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "A", AA.getText().toString());
                                        list_multiple.add(answer1);
                                        Answer answer2 = new Answer(problemID, "B", BB.getText().toString());
                                        list_multiple.add(answer2);
                                        Answer answer3 = new Answer(problemID, "C", CC.getText().toString());
                                        list_multiple.add(answer3);
                                        Answer answer4 = new Answer(problemID, "D", DD.getText().toString());
                                        list_multiple.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_multiple.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_multiple.get(i).getProblemID() + "&tag=" + list_multiple.get(i).getTag() + "&content=" + list_multiple.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = INTENT;
                                        handler.sendMessage(msg);
                                    }
                                    if (fill.getVisibility() == View.VISIBLE) { //题型为填空题
                                        String right=one.getText().toString()+","+two.getText().toString()+","+three.getText().toString()+","+four.getText().toString();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID  + "&right=" + right+"&type="+2;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

                                                    }
                                                    conn.disconnect();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Answer answer1 = new Answer(problemID, "1", one.getText().toString());
                                        list_fill.add(answer1);
                                        Answer answer2 = new Answer(problemID, "2", two.getText().toString());
                                        list_fill.add(answer2);
                                        Answer answer3 = new Answer(problemID, "3", three.getText().toString());
                                        list_fill.add(answer3);
                                        Answer answer4 = new Answer(problemID, "4", four.getText().toString());
                                        list_fill.add(answer4);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int i = 0; i < list_fill.size(); i++) {
                                                        String path = "http://mdzs.free.svipss.top/storageAnswer?";
                                                        URL url = new URL(path);
                                                        //打开httpurlconnection
                                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                        String data = "problemID=" + list_fill.get(i).getProblemID() + "&tag=" + list_fill.get(i).getTag() + "&content=" + list_fill.get(i).getContent();
                                                        conn.setRequestProperty("Charset", "UTF-8");
                                                        conn.setRequestProperty("connection", "keep-alive");
                                                        conn.setDoOutput(true); //指定输出模式
                                                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                        int code = conn.getResponseCode();
                                                        if (code == 200) {

                                                        }
                                                        conn.disconnect();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        Message msg = new Message();
                                        msg.what = INTENT;
                                        handler.sendMessage(msg);
                                    }
                                    if (judge.getVisibility() == View.VISIBLE) { //题型为判断题
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID +"&right=" + selectText2+ "&type="+3;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

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
                                    if (shorts.getVisibility() == View.VISIBLE) { //题型为简答题
                                        String right=shorts.getText().toString();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String path = "http://mdzs.free.svipss.top/storageRight?";
                                                    URL url = new URL(path);
                                                    //打开httpurlconnection
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                                                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                                                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                                                    String data = "problemID=" + problemID +"&right=" + right+ "&type="+4;
                                                    conn.setRequestProperty("Charset", "UTF-8");
                                                    conn.setRequestProperty("connection", "keep-alive");
                                                    conn.setDoOutput(true); //指定输出模式
                                                    conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                                                    int code = conn.getResponseCode();
                                                    if (code == 200) {

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
                                }
                                conn.disconnect();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String user;
            switch (msg.what){
                case TEST_USER_SELECT:
                    int num = (int) msg.obj;
                    number.setText("第"+(num+1)+"题");
                    break;
                case MESS1 :
                    title.setText(null);
                    aa.setText(null);
                    bb.setText(null);
                    cc.setText(null);
                    dd.setText(null);
                    ddlCity.setSelection(0, true);
                    break;
                case MESS2 :
                    title.setText(null);
                    AA.setText(null);
                    BB.setText(null);
                    CC.setText(null);
                    DD.setText(null);
                    ddlCity.setSelection(0, true);
                    break;
                case MESS3 :
                    title.setText(null);
                    one.setText(null);
                    two.setText(null);
                    three.setText(null);
                    four.setText(null);
                    ddlCity.setSelection(0, true);
                    break;
                case MESS4 :
                    title.setText(null);
                    ddlCity.setSelection(0, true);
                    break;
                case MESS5 :
                    title.setText(null);
                    shorts.setText(null);
                    ddlCity.setSelection(0, true);
                    break;
                case INTENT :
                    Intent intent=new Intent(TestProblemActivity.this,LessenTestActivity.class);
                    startActivity(intent);
                    break;

            }
        }

    };
    public void init(){
        single=findViewById(R.id.single);
        multiple=findViewById(R.id.multiple);
        fill=findViewById(R.id.fill);
        judge=findViewById(R.id.judge);
        shorts=findViewById(R.id.shorts);
        group1=findViewById(R.id.radioGroup);
        group2=findViewById(R.id.radioGroup1);
        name=findViewById(R.id.topic);

        A=findViewById(R.id.A);
        B=findViewById(R.id.B);
        C=findViewById(R.id.C);
        D=findViewById(R.id.D);

        number=findViewById(R.id.number);
        title=findViewById(R.id.title);
        next=findViewById(R.id.next);
        finish=findViewById(R.id.finish);

        aa=findViewById(R.id.aa);
        bb=findViewById(R.id.bb);
        cc=findViewById(R.id.cc);
        dd=findViewById(R.id.dd);

        AA=findViewById(R.id.AA);
        BB=findViewById(R.id.BB);
        CC=findViewById(R.id.CC);
        DD=findViewById(R.id.DD);

        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);

    }
}