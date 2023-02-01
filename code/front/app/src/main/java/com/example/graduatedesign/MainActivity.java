package com.example.graduatedesign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.graduatedesign.adapter.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int[] TAB_TITLES = new int[]{R.string.msg, R.string.lessen, R.string.me};

    private final int[] TAB_IMGS = new int[]{R.drawable.msg_selector, R.drawable.lessen_selector
            , R.drawable.me_selector};

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);
        top=findViewById(R.id.top);
        ButterKnife.bind(this);
        // 初始化页卡
        initPager();
        setTabs(tabLayout, getLayoutInflater(), TAB_TITLES, TAB_IMGS);

    }

    private void initPager() {
        PagerAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(), false);
                top.setText(TAB_TITLES[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.menu_item, null);
            // 使用自定义视图，目的是为了便于修改，也可使用自带的视图
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.txt_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }

}