package com.ityingli.www.mynews.Pager;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ityingli.www.mynews.ActivityPack.RiBao_Item_ContentActivity;
import com.ityingli.www.mynews.Adapter.RiBao_RecycleView_Adapter;
import com.ityingli.www.mynews.Adapter.ViewPager_carousel_Adapter;
import com.ityingli.www.mynews.Bean.RiBao_Viewpager;
import com.ityingli.www.mynews.Bean.RiBao_recycleView_item;
import com.ityingli.www.mynews.Pager.URLPake.RibaoFragment_Uri;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.Util.DensityUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RiBaoFragment2 extends Fragment {
   private String TAG  ="RiBaoFragment2";
    private View view;
    private RecyclerView recycleView;
    private List<RiBao_recycleView_item> recycleViewDatas;
    private List<RiBao_Viewpager> viewPagerDatas;
    final int MESSGE_ONE = 1;
    final int MESSGE_TWO =2;
    final int MESSAGE_THREE =3;
    Intent intent;
    private RiBao_RecycleView_Adapter recycleViewAdapter;

    private ViewPager_carousel_Adapter viewpagerAdapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch(msg.what){
                case MESSGE_ONE:
                    recycleViewAdapter = (RiBao_RecycleView_Adapter)new RiBao_RecycleView_Adapter(recycleViewDatas,getContext(),topView);
                    recycleView.setAdapter(recycleViewAdapter);
                    /*
                    * 并且设置点击事件
                    * */
                    initRecycleViewItemOnClick();
                    break;

                case MESSGE_TWO:
                    viewpagerAdapter = new ViewPager_carousel_Adapter(viewPagerDatas,getContext());
                    viewPager.setAdapter(viewpagerAdapter);
                    break;
                case MESSAGE_THREE:
                    viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%5);
                    handler.removeMessages(MESSAGE_THREE);
                    handler.sendEmptyMessageDelayed(MESSAGE_THREE,3000);
                    break;
            }
        }
    };

    private View topView;
    private ViewPager viewPager;
    private LinearLayout dot_ll_id;
    private int selectingPointIndex;
    private SwipeRefreshLayout swipeRefreshLayout;
    /*
    * 之前的时间
    * */
    private int beforyDay = 1;    //1-1=0
    private FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_ribao_fagment2,container,false);
        /*
        * 这里使用一个RecycleView的方法添加头部的方法
        * */
       /*
       * 获取当前时间
       * */
       getNowDate(beforyDay);

        /*
        * 初始化变量
        * */
        initView();
        /*
        * 初始化事件
        * */
        initEvent();

        /*
        * 初始化数据
        * */
        initDatas();
        /*
        * 监听recycleView是否滑动到底部
        * */
        initRecycleViewScrollBottom();
        /*
        * 设置适配器
        * */
     //   recycleView.setAdapter(new RiBao_RecycleView_Adapter(recycleViewDatas,getContext()));
         /*
         * 设置布局管理
         * */
         recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
        * 设置分割间距
        * */
        recycleView.addItemDecoration(new MyRecycleViewItemDecorationo());


        return view;
    }

    /*
       * 给RecycleView Item设置点击事件
       * */
    private void initRecycleViewItemOnClick() {

        recycleViewAdapter.setOnItemClick(new RiBao_RecycleView_Adapter.OnItemClick() {
            @Override
            public void ItemClick(View view, int position) {
               // Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                if (intent == null) intent = new Intent(getContext(), RiBao_Item_ContentActivity.class);
                int id = recycleViewDatas.get(position).getId();
                intent.putExtra("useId",id);
                Log.e(TAG, "ItemClick: "+id);
                getContext().startActivity(intent);
            }
        });
    }
    /*
    * 获取当前的时间
    * */
    private String  getNowDate(int beforyDay) {   //参数代表的是多少天前的
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,beforyDay);
        SimpleDateFormat simpledateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = calendar.getTime();
        String stringDate = simpledateFormat.format(date);
		
        //Toast.makeText(getContext(),stringDate,Toast.LENGTH_SHORT).show();
        return stringDate;        //返回一个日期，传入-1，就返回昨天的日期
    }

    private void initRecycleViewScrollBottom() {
        //监听recyclView是否滑动到底部
      recycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
          public boolean isLastReflash;

          @Override
          public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
              /*
              * 只要是滑动,不在顶部了
              * */
              if(recyclerView.computeVerticalScrollOffset()>0){
                  floatingActionButton.setVisibility(View.VISIBLE);
              }else{
                  floatingActionButton.setVisibility(View.GONE);
              }

              /*
              * 滑动停止之后检测是否滑动到底部
              **/
              if(newState == RecyclerView.SCROLL_STATE_IDLE &&isLastReflash){
                   if(recycleView.computeVerticalScrollExtent()+recyclerView.computeVerticalScrollOffset()>=recyclerView.computeVerticalScrollRange()){
                      // Toast.makeText(getContext(),"滑动到底部",Toast.LENGTH_SHORT).show();
                       /*
                       * 在这里更新滑动到底部的值
                       * */
                       beforyDay = beforyDay-1;
                       /*
                       * 滑动到底部的时候获取数据添加
                       * */
                       getMostDatas();
                       /*
                       * 提示适配器
                       * */
                       recycleViewAdapter.notifyDataSetChanged();
                   }
              }
          }

          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              if(dy>0){
                  isLastReflash = true;
              }else{
                  isLastReflash = false;
              }
          }
      });
    }

    /*
    * 获取更多数据
    * */
    private void getMostDatas() {
        OkHttpClient okhttpClient = new OkHttpClient();
        Request.Builder build = new Request.Builder();
        getHistoryDatas(okhttpClient, build);
    }

    private void getHistoryDatas(OkHttpClient okhttpClient, Request.Builder build) {
        Request request = build.get().url(RibaoFragment_Uri.history+getNowDate(beforyDay)).build();
        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("RiBaoFragment2","Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("RiBaoFragment2","onResponse");
                String string  =response.body().string();
                /*
                * 数据已经添加
                * */
                try {
                    JSONObject Object  = new JSONObject(string);
                    JSONArray jsonArray = Object.getJSONArray("stories");
                    for(int i = 0 ;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String imgUri = (String)jsonObject.getJSONArray("images").get(0);
                        String title = (String)jsonObject.get("title");
                        int id = (int)jsonObject.get("id");
                        RiBao_recycleView_item item = new RiBao_recycleView_item(imgUri,title,id);
                        recycleViewDatas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ImageView selectingPoint = (ImageView)dot_ll_id.getChildAt(position);
                selectingPoint.setPressed(true);
                ImageView iv2 = (ImageView)dot_ll_id.getChildAt(selectingPointIndex);
                iv2.setPressed(false);
                selectingPointIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /*
        * 发送消息设置viewpager自动切换页卡
        * */
        handler.sendEmptyMessage(MESSAGE_THREE);

        /*m
        * 刷新
        * */
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#000000"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
                recycleViewAdapter.notifyDataSetChanged();
                viewpagerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*
        * 悬浮按钮的事件
        * */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager  linearLayoutManager=  (LinearLayoutManager)recycleView.getLayoutManager();
                linearLayoutManager.scrollToPositionWithOffset(0,0);
                floatingActionButton.setVisibility(View.GONE);
            }
        });
    }

    private void initDatas() {
        recycleViewDatas =  new ArrayList<>();
        viewPagerDatas = new ArrayList<>();
        /*
        * 连网获取
        * */
        OkHttpClient okhttpClient = new OkHttpClient();
        Request.Builder build = new Request.Builder();
        Request request = build.get().url("http://news-at.zhihu.com/api/4/news/latest").build();
        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e(TAG,"联网失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功获取返回的json数据
                String string  = response.body().string();
                /*
                * 获取头部的数据
                * */
                getViewPagerJsonData(string);
                handler.sendEmptyMessage(MESSGE_TWO);

                getRecycleViewDatas(string);
              /*
              * 发送一个空的消息
              * RecycleView设置适配器
              * */
                handler.sendEmptyMessage(MESSGE_ONE);
            }
        });

     }


    /*
  * 解释获取到的json数据
  * */
    private void getViewPagerJsonData(String string) {
        try {
            JSONObject Object = new JSONObject(string);
            JSONArray jsonArray = Object.getJSONArray("top_stories");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject itemObject = (JSONObject) jsonArray.get(i);
                String imgUri = (String)itemObject.get("image");
                String title  = (String)itemObject.get("title");
                RiBao_Viewpager item = new RiBao_Viewpager(title,imgUri);
                viewPagerDatas.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    private void getRecycleViewDatas(String string) {
        //模拟第一条数据（这条数据没有用到）
        recycleViewDatas.add(null);
        try {
            JSONObject Object  = new JSONObject(string);
            JSONArray jsonArray = Object.getJSONArray("stories");
            for(int i = 0 ;i<jsonArray.length();i++){
               JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String imgUri = (String)jsonObject.getJSONArray("images").get(0);
                String title = (String)jsonObject.get("title");
                int id = (int)jsonObject.get("id");
                RiBao_recycleView_item item = new RiBao_recycleView_item(imgUri,title,id);
                recycleViewDatas.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        /*
        * 头部
        * */
        topView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ribao_recycleview_topview, recycleView,false);
        viewPager = (ViewPager)topView.findViewById(R.id.viewpager_carousel);
        dot_ll_id = (LinearLayout)topView.findViewById(R.id.dot_ll_id);

        /*
       * 设置点数
       * */
        //这里的5是对应的5张图片
        for(int i = 0 ;i<5;i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.dot_select);
            LinearLayout.LayoutParams lp  = new  LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(),5),DensityUtil.dip2px(getContext(),5));
            lp.leftMargin = DensityUtil.dip2px(getContext(),5);
            iv.setLayoutParams(lp);
            dot_ll_id.addView(iv);
        }
        ImageView iv = (ImageView)dot_ll_id.getChildAt(selectingPointIndex );
        iv.setPressed(true);




        recycleView = (RecyclerView)view.findViewById(R.id.recycleView);
        /*
        * 找到刷新控件
        * */
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ffffff"));
        /*
        * 悬浮按钮
        * */
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);

    }


    /*
    * RecycleView的分割间距
    * */
    class MyRecycleViewItemDecorationo extends  RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = DensityUtil.dip2px(getContext(),10);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeMessages(MESSAGE_THREE);
    }
}


