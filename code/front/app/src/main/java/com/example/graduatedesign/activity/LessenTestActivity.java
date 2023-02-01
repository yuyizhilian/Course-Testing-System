package com.example.graduatedesign.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.adapter.LessenTestFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class LessenTestActivity extends AppCompatActivity {

    private final int[] TAB_TITLES = new int[]{R.string.already_publish,R.string.wait_publish};
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView top;

    String lessenID;

    PopupWindow popupWindow;//定义popupWindow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_lessen_test);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab);
        top=findViewById(R.id.name);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        lessenID=intent.getStringExtra("lessenID");
        top.setText(title);
        ButterKnife.bind(this);
        // 初始化页卡
        initPager();
        setTabs(tabLayout, getLayoutInflater(), TAB_TITLES);
    }

    //菜单按钮的单机事件
    public void OnMenu(View v){
        //获取自定义菜单的布局文件
        View popupWindow_view=getLayoutInflater().inflate(R.layout.menu,null,false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button add=popupWindow_view.findViewById(R.id.add);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button inter=popupWindow_view.findViewById(R.id.inter);
        //创建popupWindow实例，设置菜单的宽度和高度
        popupWindow=new PopupWindow(popupWindow_view, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        //设置菜单显示在按钮的下面
        popupWindow.showAsDropDown(findViewById(R.id.menu),0,0);
        //单机其它位置隐藏菜单
        popupWindow_view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //如果菜单存在并且处于显示状态
                if (popupWindow!=null&&popupWindow.isShowing()){
                    popupWindow.dismiss();//关闭菜单
                    popupWindow=null;
                }
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LessenTestActivity.this,TestProblemActivity.class);
                intent.putExtra("lessenID",lessenID);
                startActivity(intent);
            }
        });


    }
    private void initPager() {
        PagerAdapter adapter = new LessenTestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees) {
        for (int i = 0; i < tabTitlees.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.test_button, null);
            // 使用自定义视图，目的是为了便于修改，也可使用自带的视图
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.txt);
            tvTitle.setText(tabTitlees[i]);
            tabLayout.addTab(tab);
        }
    }
}