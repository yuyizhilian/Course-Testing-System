package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.example.graduatedesign.R;

public class LessenDetailsActivity extends AppCompatActivity {

    private TextView name;
    private static final int MESSAGE = 1;
    private String lessenName;

    private TextView test;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_lessen_details);
        name = findViewById(R.id.name);
        test = findViewById(R.id.test);
        String title=(String)test.getText();
        Intent intent = getIntent();
        lessenName = intent.getStringExtra("lessenName");
        String lessenID=intent.getStringExtra("lessenID");
        name.setText(lessenName);
        test.setOnClickListener(v -> {
            Intent intent1=new Intent(this,LessenTestActivity.class);
            intent1.putExtra("title",title);
            intent1.putExtra("lessenID",lessenID);
            startActivity(intent1);
        });

    }


}