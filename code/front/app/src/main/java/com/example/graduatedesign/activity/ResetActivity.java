package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graduatedesign.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ResetActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private EditText sure;
    private EditText proof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_reset);

        account=findViewById(R.id.account);
        password=findViewById(R.id.password);
        sure=findViewById(R.id.sure);
        Button reset=findViewById(R.id.reset);


        reset.setOnClickListener(v -> {
            String accountName=account.getText().toString();
            String passwordNum=password.getText().toString();
            String pwd=sure.getText().toString();
            if(!passwordNum.equals(pwd)){
                Toast.makeText(ResetActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            }
            //重置密码接口
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path="http://mdzs.free.svipss.top/reset?";
                        URL url = new URL(path);
                        //打开httpurlconnection
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                        String data="userID="+ URLEncoder.encode(accountName,"utf-8")+"&userNewPwd="+ URLEncoder.encode(passwordNum,"utf-8");
                        conn.setRequestProperty("Content-length",String.valueOf(data.length()));
                        conn.setDoOutput(true); //指定输出模式
                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                        int code = conn.getResponseCode();         // 获取response状态，200表示成功获取资源，404表示资源不存在
                        if (code==200){
                            InputStream is=conn.getInputStream();
                            BufferedReader br=new BufferedReader(new InputStreamReader(is));
                            StringBuffer buffer=new StringBuffer();
                            String len=null;
                            while((len=br.readLine())!=null){
                                buffer.append(len);
                            }
                            JSONObject jsonObject = new JSONObject(buffer.toString());
                            String result = jsonObject.optString("data", null);
                            Boolean b= Boolean.valueOf(result);
                            if(!b){
                                Looper.prepare();
                                Toast.makeText(ResetActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else {
                                Intent intent = new Intent(ResetActivity.this,LoginActivity.class);
                                startActivity(intent);
                                Looper.prepare();
                                Toast.makeText(ResetActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}