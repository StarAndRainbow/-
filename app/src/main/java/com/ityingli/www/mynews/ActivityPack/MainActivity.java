package com.ityingli.www.mynews.ActivityPack;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ityingli.www.mynews.Pager.HomeFragment;
import com.ityingli.www.mynews.Pager.RiBaoFragment2;
import com.ityingli.www.mynews.Pager.meFragment;
import com.ityingli.www.mynews.Pager.photoFragment;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.Util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/*
* create 2017/5/12
* this is Android 5.0 Project
* */
public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton homeButton;
    private RadioButton liveButton;
    private RadioButton videoButton;
    private RadioButton meButton;
    private RadioButton importantNews;
    private FrameLayout fragmentLayout;
    private  List<Fragment>  fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        replace_Fragment(0);
        initEvent();
        //设置第一个button选中
        homeButton.setChecked(true);
    }


    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
       /// fragments.add(new RiBaoFragment());
        fragments.add(new RiBaoFragment2());
        fragments.add(new photoFragment());
        //fragments.add(new VideoFragment());
        fragments.add(new meFragment());

    }

    private void initEvent() {
        /*
        * RaidoGroup更改之后，就去更改对应的Fragment显示
        * */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.homeButton:
                        //Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
                        replace_Fragment(0);
                        break;
                    case R.id.importantNewsButton:
                       // Toast.makeText(MainActivity.this,"要闻", Toast.LENGTH_SHORT).show();
                        replace_Fragment(1);
                        break;
                    case R.id.liveButton:
                       // Toast.makeText(MainActivity.this, "图片", Toast.LENGTH_SHORT).show();
                        replace_Fragment(2);
                        break;
                   // case R.id.videoButton:
                       // Toast.makeText(MainActivity.this, "视频", Toast.LENGTH_SHORT).show();
                    //    replace_Fragment(3);
                    //    break;
                    case R.id.meButton:
                       // Toast.makeText(MainActivity.this, "我", Toast.LENGTH_SHORT).show();
                        replace_Fragment(3);   //4原来是3
                        break;

                }
            }
        });
    }

    private void replace_Fragment(int indexNumber) {
         getSupportFragmentManager().beginTransaction().replace(R.id.frameeLayout,fragments.get(indexNumber)).commit();
    }


    private void initView() {
         radioGroup = (RadioGroup)findViewById(R.id.RadioGroup_id);
        homeButton = (RadioButton)findViewById(R.id.homeButton);
        liveButton = (RadioButton)findViewById(R.id.liveButton);
        //videoButton = (RadioButton)findViewById(R.id.videoButton);
        meButton = (RadioButton)findViewById(R.id.meButton);
        importantNews = (RadioButton)findViewById(R.id.importantNewsButton);
         fragmentLayout= (FrameLayout)findViewById(R.id.frameeLayout);
        /*
        * 设置RaidoButton的图片大少
        * */
        initRaidioButton();
    }

    private void initRaidioButton() {
        int width_height_value = DensityUtil.dip2px(MainActivity.this,20);
        Drawable home_button_bg = getResources().getDrawable(R.drawable.home_button_bg);
        home_button_bg.setBounds(0,0,width_height_value,width_height_value);
        homeButton.setCompoundDrawables(null,home_button_bg,null,null);

        Drawable important_news_bg = getResources().getDrawable(R.drawable.important_news_bg);
        important_news_bg.setBounds(0,0,width_height_value,width_height_value);
        importantNews.setCompoundDrawables(null,important_news_bg,null,null);

        Drawable live_button_bg = getResources().getDrawable(R.drawable.live_button_bg);
        live_button_bg.setBounds(0,0,width_height_value,width_height_value);
        liveButton.setCompoundDrawables(null,live_button_bg,null,null);

        Drawable me_button_bg = getResources().getDrawable(R.drawable.me_button_bg);
        me_button_bg.setBounds(0,0,width_height_value,width_height_value);
        meButton.setCompoundDrawables(null,me_button_bg,null,null);

        //Drawable video_bg = getResources().getDrawable(R.drawable.video_bg);
        //video_bg.setBounds(0,0,width_height_value,width_height_value);
        //videoButton.setCompoundDrawables(null,video_bg,null,null);
    }
}
