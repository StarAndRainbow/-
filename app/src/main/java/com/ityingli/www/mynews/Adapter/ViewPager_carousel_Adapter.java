package com.ityingli.www.mynews.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ityingli.www.mynews.Bean.RiBao_Viewpager;
import com.ityingli.www.mynews.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class ViewPager_carousel_Adapter extends PagerAdapter {

   Context mcontext;
    List<RiBao_Viewpager> ImgDatas;
    public ViewPager_carousel_Adapter(List<RiBao_Viewpager> ImgDatas, Context mcontext){
        this.ImgDatas = ImgDatas;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return ImgDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view  =LayoutInflater.from(mcontext).inflate( R.layout.ribao_viewpager_item,container,false);
        ImageView img_iv = (ImageView)view.findViewById(R.id.img_iv);
        TextView title_tv= (TextView)view.findViewById(R.id.title_tv);
        Glide.with(mcontext).load(ImgDatas.get(position).getImgUri()).into(img_iv);
        img_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        title_tv.setText(ImgDatas.get(position).getTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(container);
    }

}
