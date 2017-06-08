package com.ityingli.www.mynews.Pager.HomePaerPack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityingli.www.mynews.ActivityPack.Top_ItemContentShowActiviity;
import com.ityingli.www.mynews.Adapter.Top_ListvViewAdapter_two;
import com.ityingli.www.mynews.Bean.homePager_topitem;
import com.ityingli.www.mynews.Pager.URLPake.HomePagerUri;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.View.TopListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/15.
 */

public class JunShiPager extends Fragment {
    public View view;
    private TopListView top_listView;
    private Top_ListvViewAdapter_two top_ListView_adapter;
    //为了加载跟多的时候可以方便加载更多，所以在这里创建
    private List<homePager_topitem> top_datas  = new ArrayList<>();
    private final int MESSAGE_ONE = 1;
    private final int MESSAGE_TWO = 2;
    private final int MESSAGE_THREE = 3;
    private final int MESSAGE_FOUR = 4;
    private final int MESSAGE_FIVE = 5;

    //模拟分页，第一次加载条数 10条,然后每次加载的时候都多加载10调，到30条的时候加载到底部
    private int firstItem=0;      //0
    private int itemCount=10;        //10
    //数据的数据
    private JSONArray dataArray;
    private String string;
    private String pullRefalshstring;
    private boolean isRemoveHeadDesc;   //默认为false；
    private TextView tvdesc;
    private LinearLayout topPage_load_ll;

    /*接收到信息之后设置适配器*/
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String refashTextDesc = "";//刷新后的文本描述
            switch (msg.what) {
                case MESSAGE_ONE:
                    topPage_load_ll.setVisibility(View.GONE);
                    top_ListView_adapter = new Top_ListvViewAdapter_two(getContext(), top_datas);
                    top_listView.setAdapter(top_ListView_adapter);
                    break;
                case MESSAGE_TWO:
                    //下拉请求到数据相同的时候


                    refashTextDesc = "没有更多数据";
                    addRefalshHead(refashTextDesc);

                    Toast.makeText(getContext(),"已经刷新",Toast.LENGTH_SHORT).show();
                    top_listView.reflashComplete();
                    // top_ListView_adapter.notifyDataSetChanged();
                    break;
                case MESSAGE_THREE:

                    refashTextDesc = "网络不给力";
                    addRefalshHead(refashTextDesc);
                    //下拉请求网络数据出错的时候，要自动把顶部隐藏回去
                    top_listView.reflashComplete();
                    break;
                case MESSAGE_FOUR:

                    refashTextDesc = "已经更新";
                    addRefalshHead(refashTextDesc);
                    //更新数据数据，数据返回改变的时候
                    top_listView.reflashComplete();
                    top_ListView_adapter.notifyDataSetChanged();
                    break;
                case MESSAGE_FIVE:
                    //移除描述添加的头
                    //  top_listView.removeView(tvdesc);
                    if(isRemoveHeadDesc) {
                        top_listView.removeHeaderView(tvdesc);
                        isRemoveHeadDesc = false;
                    }
                    break;
            }
        }
    };



    /*
    * 添加一个刷新后的头布局
    * */
    private void addRefalshHead(String textdesc) {
        if(isRemoveHeadDesc){
            top_listView.removeHeaderView(tvdesc);
            isRemoveHeadDesc = false;
        }
        isRemoveHeadDesc = true;
        tvdesc = new TextView(getContext());
        tvdesc.setText(textdesc);
        tvdesc.setBackgroundColor(Color.parseColor("#A4D8F5"));
        tvdesc.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvdesc.setLayoutParams(lp);
        top_listView.addHeaderView(tvdesc);

        //添加头部描述之后，四秒移除
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.removeMessages(MESSAGE_FIVE);
                handler.sendEmptyMessage(MESSAGE_FIVE);
            }
        }).start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_toppager,container,false);
        initView();
        initData();
        initRefalshCallBack();
        initEvent();
        return view;
    }

    private void initEvent() {
        /*
        * ListView的点击事件
        * */
        top_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(),"=="+position,Toast.LENGTH_SHORT).show();
                /*
                * 默认点击第一个item的时候显示的是1（有刷新头）
                * 当刷新过后点击的时候显示的是2（有刷新头和刷新显示数据的头）
                *当刷新信息显示头消失的时候显示的是1
                * */


               /* if(isRemoveHeadDesc) {   //头描述还没移除的收
                    Toast.makeText(getContext(), position+"=22=" + (position-2), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), position+"-11-" + (position-1), Toast.LENGTH_SHORT).show();
                }*/

                if(isRemoveHeadDesc) {   //头描述还没移除的收
                    position = position -2;
                }else{
                    position = position-1;
                }

                Intent itemContentIntent = new Intent(getContext(), Top_ItemContentShowActiviity.class);
                itemContentIntent.putExtra("itemKey",top_datas.get(position).url);
                startActivity(itemContentIntent);
            }
        });
    }

    private void initRefalshCallBack() {
        //在控件和数据都初始化之后呢，我们设置回掉接口，继续加载30条
        top_listView.setonLoadingFooter(new TopListView.OnLoadingFooter() {
            @Override
            public void loadingFooter() {
                if(itemCount<=30){
                    //添加要增加的数据
                    eachLoading();
                    top_ListView_adapter.notifyDataSetChanged();
                    //数据加载完成之后把底部隐藏
                    //top_listView.loadComplete();
                }else{
                    //设置footer，进度条隐藏，文本显示:暂时没有更多数据
                    top_listView.noMostDatas();
                }
            }
        });




        //下拉刷新回调接口
        //设置下拉刷新的接口
        top_listView.setHeadPUllReflash(new TopListView.HeadPUllReflash() {
            @Override
            public void pullRefalsh() {
                pullRefalshGetData();
            }
        });
    }


    private void pullRefalshGetData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(HomePagerUri.JUNSHIURL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(MESSAGE_THREE);//发送消息，请求出错
                Log.e("MainActivity", "onFailure: " );
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                pullRefalshstring = response.body().string();
                if(string.equals(pullRefalshstring)){
                    Log.e("pullRefalsh","相同");
                    handler.sendEmptyMessage(MESSAGE_TWO);//发送消息，已经更新完数据，但数据没有更新
                }else{
                    Log.e("pullRefalsh","不相同");
                    getItemDatas(pullRefalshstring); //取重新获取数据
                    handler.sendEmptyMessage(MESSAGE_FOUR);//发送消息，发现数据有更新的时候
                }

            }
        });
    }


    private void initData() {
        //1.okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2构造Request,
        //builder.get()代表的是get请求，url方法里面放的参数是一个网络地址
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(HomePagerUri.JUNSHIURL).build();

        //3将Request封装成call
        Call call = okHttpClient.newCall(request);

        //4，执行call，这个方法是异步请求数据
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //失败调用
                Log.e("MainActivity", "onFailure: " );
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //成功调用
                string = response.body().string();
                getItemDatas(string);
            }
        });

    }

    /*
    *解释json数据
    * */
    private void getItemDatas(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject result_jsonObject=jsonObject.getJSONObject("result");
            dataArray = result_jsonObject.getJSONArray("data");
            eachLoading();
             /*
             * 数据获取完之后发送一条消息，并且设置设配器
             * */
            handler.sendEmptyMessage(MESSAGE_ONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //每一次的加载数据
    private void eachLoading() {
        for(int i = firstItem;i<itemCount;i++) {
            try {
                homePager_topitem topitem = new homePager_topitem();
                topitem.title = dataArray.getJSONObject(i).getString("title");
                topitem.date = dataArray.getJSONObject(i).getString("date");
                topitem.category = dataArray.getJSONObject(i).getString("category");
                topitem.author_name = dataArray.getJSONObject(i).getString("author_name");
                topitem.url = dataArray.getJSONObject(i).getString("url");
                topitem.thumbnail_pic_s = dataArray.getJSONObject(i).getString("thumbnail_pic_s");
            /*判断字符串中是否包含三张图片的路径，如果包含则把该type判断为1，否则判断type为0*/
                boolean isHasImg3;
                isHasImg3 = dataArray.getJSONObject(i).has("thumbnail_pic_s03");
                if (isHasImg3) {
                    topitem.thumbnail_pic_s02 = dataArray.getJSONObject(i).getString("thumbnail_pic_s02");
                    topitem.thumbnail_pic_s03 = dataArray.getJSONObject(i).getString("thumbnail_pic_s03");
                    topitem.type = 1;
                }
                top_datas.add(topitem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        firstItem = itemCount;
        itemCount+=10;
    }
    private void initView(){
        top_listView = (TopListView)view.findViewById(R.id.top_listView);
        topPage_load_ll = (LinearLayout)view.findViewById(R.id.topPage_load_ll);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup)view.getParent()).removeView(view);
    }
}
