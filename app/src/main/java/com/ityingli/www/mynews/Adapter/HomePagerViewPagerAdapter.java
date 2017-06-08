package com.ityingli.www.mynews.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HomePagerViewPagerAdapter  extends FragmentPagerAdapter {
    private List<Fragment>  datas;
    public HomePagerViewPagerAdapter(FragmentManager fm,List<Fragment>  datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

}