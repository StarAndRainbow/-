package com.ityingli.www.mynews.Pager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.Adapter.Photo_ViewPager_Adapter;
import com.ityingli.www.mynews.Pager.PhotoFragmentPack.photo_classification;
import com.ityingli.www.mynews.Pager.PhotoFragmentPack.photo_home;
import com.ityingli.www.mynews.R;

import java.util.ArrayList;
import java.util.List;

public class photoFragment extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager content_viewPager;
    /*
    * fragment的数据
    * */
    List<Fragment> fragmentdatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_photo,container,false);
        initView();
        initDatas();
        initAdapter();
        /*
        * viewPager和TagLayout联动起来
        * */
        tabLayout.setupWithViewPager(content_viewPager);
        /*
        * 解决TabLayotu和ViewPger联动的一些小问题
        * */
        tabLayout.getTabAt(0).setText("推荐");
        tabLayout.getTabAt(1).setText("分类");
        return view;
    }

    private void initAdapter() {
        content_viewPager.setAdapter(new Photo_ViewPager_Adapter(getChildFragmentManager(),fragmentdatas));
    }

    private void initDatas() {
        tabLayout.addTab(tabLayout.newTab().setText("热门"));
        tabLayout.addTab(tabLayout.newTab().setText("分类"));

        fragmentdatas = new ArrayList<>();
        fragmentdatas.add(new photo_home());
        fragmentdatas.add(new photo_classification());
    }


    private void initView(){
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        content_viewPager  = (ViewPager)view.findViewById(R.id.content_viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       ((ViewGroup)view.getParent()).removeView(view);
    }
}
