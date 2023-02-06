package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graduatedesign.MainActivity;
import com.example.graduatedesign.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox remember;

    private EditText account;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_login);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        account=findViewById(R.id.account);
        password=findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button secret=findViewById(R.id.secret);
        //可以选择是否记住密码
        remember=findViewById(R.id.remember);
        Button register = findViewById(R.id.register);
        Log.d("TAG", "onCreate: ");
        boolean isRemember=preferences.getBoolean("remember_password",false);
        if(isRemember){
            String accountName=preferences.getString("account","");
            String passwordNum=preferences.getString("password","");
            account.setText(accountName);
            password.setText(passwordNum);
            remember.setChecked(true);
        }

        register.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //  登录点击监听事件
        login.setOnClickListener(v -> {
            String accountName=account.getText().toString();
            String passwordNum=password.getText().toString();

            new Thread(){
                @Override
                public void run() {
                    try {
                        String path="http://mdzs.free.svipss.top/login?";
                        URL url = new URL(path);
                        //打开httpurlconnection
                        Log.d("11111111", "run: ");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
                        String data="userID="+ accountName+"&userPwd="+ passwordNum;
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
                            Log.d("2222222", "run: ");
                            JSONObject jsonObject = new JSONObject(buffer.toString());
                            String result = jsonObject.optString("data", null);
                            Boolean b= Boolean.valueOf(result);
                            if(b){
                                editor=preferences.edit();
                                if(remember.isChecked()){
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("account",accountName);
                                    editor.putString("password",passwordNum);
                                }else{
                                    editor.clear();
                                }
                                editor.apply();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                finish();
                            }else{
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"登录失败,账号或者密码错误",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();



        });

        secret.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this, ResetActivity.class);
            startActivity(intent);
        });
    }
}