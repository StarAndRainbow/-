package com.ityingli.www.mynews.ActivityPack;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ityingli.www.mynews.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class Photo_content_bigPhoto extends AppCompatActivity {

    private ViewPager viewPager;

    /*
    * 数据源
    * */
    private List<View> viewpager_Datas = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_content_big_photo);
        String imgurl = getIntent().getStringExtra("imgurl");
        PhotoView photoview  =(PhotoView)findViewById(R.id.photoView);
        Glide.with(Photo_content_bigPhoto.this).load(imgurl).placeholder(R.drawable.loading).error(R.drawable.error).into(photoview);
    }
}
