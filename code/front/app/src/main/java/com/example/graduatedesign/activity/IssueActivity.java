package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.graduatedesign.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IssueActivity extends AppCompatActivity {

    TimePickerView pvTime;

    TextView starts;
    TextView ends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_issue);
        starts=findViewById(R.id.starts);
        ends=findViewById(R.id.ends);
        starts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击组件的点击事件
                if (pvTime!=null){
                    pvTime.show(starts);
                }
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
    private String getTimes(Date date) {//年月日时分秒格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}