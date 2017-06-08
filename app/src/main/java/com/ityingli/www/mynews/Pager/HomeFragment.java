package com.ityingli.www.mynews.Pager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.Adapter.HomePagerViewPagerAdapter;
import com.ityingli.www.mynews.Pager.HomePaerPack.CaiJinPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.JunShiPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.KejiUriPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.ShiShangPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.guojinPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.guoneiPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.shehuiPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.tiyuPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.topPager;
import com.ityingli.www.mynews.Pager.HomePaerPack.yulePager;
import com.ityingli.www.mynews.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private TabLayout topTabLayout;
    private ViewPager viewPager;
    /*
    * TabLayout的数据
    * */
    String []  tabItemDatas;
    /*
    * ViewPager的数据
    * */
    private List<Fragment> fragmentsDatas ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_homepager,container,false);
        /*
        * 初始化数据用于,tabLayout
        * */
         initData();
        /*
        * 初始化控件
        * */
       initView();
        /*
        * 初始化适配器
        * */
        initAdapter();

         /*
        * TabLayout和ViewPager的联合
        * */
        topTabLayout.setupWithViewPager(viewPager);
        for(int i = 0 ;i<tabItemDatas.length;i++){
            topTabLayout.getTabAt(i).setText(tabItemDatas[i]);
        }
        /*
        * 设置默认选中第一个
        * */
        topTabLayout.getTabAt(0).select();
        viewPager.setCurrentItem(0);

        return view;
    }


    private void initAdapter() {
        viewPager.setAdapter(new HomePagerViewPagerAdapter(getChildFragmentManager(),fragmentsDatas));
    }


    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private void initData(){
        /*moniTabLayout的数据*/
        tabItemDatas = new String[]{"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
        /*
        * ViwePager的数据
        * */
        fragmentsDatas = new ArrayList<>();
        fragmentsDatas.add(new topPager());
        fragmentsDatas.add(new shehuiPager());
        fragmentsDatas.add(new guoneiPager());
        fragmentsDatas.add(new guojinPager());
        fragmentsDatas.add(new yulePager());
        fragmentsDatas.add(new tiyuPager());
        fragmentsDatas.add(new JunShiPager());
        fragmentsDatas.add(new KejiUriPager());
        fragmentsDatas.add(new CaiJinPager());
        fragmentsDatas.add(new ShiShangPager());
    }


    private void initView() {
        topTabLayout = (TabLayout)view.findViewById(R.id.topTablayout);
        for(int i = 0;i<tabItemDatas.length;i++){
            topTabLayout.addTab(topTabLayout.newTab().setText(tabItemDatas[i]));
        }
        
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // ((ViewGroup)view.getParent()).removeView(view);
        //((ViewGroup)view.getParent()).removeView(view);
    }
}
