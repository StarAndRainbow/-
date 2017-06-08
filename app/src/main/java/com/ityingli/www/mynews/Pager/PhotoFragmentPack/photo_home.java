package com.ityingli.www.mynews.Pager.PhotoFragmentPack;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.ActivityPack.Photo_content_bigPhoto;
import com.ityingli.www.mynews.Adapter.Photo_recycleView_Adapter;
import com.ityingli.www.mynews.Pager.URLPake.PhoToFragmentUri;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.Util.DensityUtil;
import com.ityingli.www.mynews.Util.parseUrlFromRegest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class photo_home extends Fragment {
    private static final String TAG = "photo_home";
    View view;
    private RecyclerView recycleView;

    /*
    * 访问网络返回的数据
    * */
    String stringurl;
    /*
    * url的数据源
    * */
    List<String> urlDatas;
    //高清图片的路径
    List<String> urlDatas2;

    final int MESSAGE_IMGDATAS = 0;
    private Photo_recycleView_Adapter adapter;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_IMGDATAS:
                    //Log.e(TAG, "handleMessage: "+urlDatas.size()+"==="+urlDatas2.size() );
                    adapter = new Photo_recycleView_Adapter(urlDatas,getContext());
                    recycleView.setAdapter(adapter);
                    /*
                    * 设置回调接口
                    * */
                    adapter.setItemClick(new Photo_recycleView_Adapter.ItemClick() {
                        @Override
                        public void itemClick(View view, int position) {
                            //Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Photo_content_bigPhoto.class);
                            //携带一个图片的网址过去
                            intent.putExtra("imgurl",urlDatas2.get(position));
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    private parseUrlFromRegest urlFromRegest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view  =   inflater.inflate(R.layout.fragment_photo_home, container, false);
        initView();
        initAdapter();
        /*
        * recycleView添加分割线
        * */
        recycleView.addItemDecoration(new ItemDecoration());
        /*
        *访问网络获取要用到的图片路径（推荐）
        **/
        geNetWorktDatas();

        return view;
    }

    /*
    * 获取网络图片
    * */
    private void geNetWorktDatas() {
         /*
         * 连接网络访问数据
         * */
        OkHttpClient client = new OkHttpClient();
        Request.Builder build = new Request.Builder();
        Request request  = build.get().url(PhoToFragmentUri.index).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+"联网失败" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onFailure: "+"联网成功" );
                stringurl = response.body().string();
                parseFromRegest(stringurl);
                /*
                * 解释好之后发一条消息
                * */
                handler.sendEmptyMessage(MESSAGE_IMGDATAS);
            }
        });
    }

    /*
    * 使用正则表达式解释返回的数据
    * */
    private void parseFromRegest(String stringurl) {

        //标清
        urlDatas = new ArrayList<>();
       /* String reg = "(\"thumbURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg)";
        Pattern pattern = Pattern.compile(reg);
        Matcher mat= pattern.matcher(stringurl);
        while(mat.find()){
            urlDatas.add(mat.group(2));
        }*/

        //高清
        urlDatas2 = new ArrayList<>();
       /* String reg_2 = "(\"objURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg)";
        Pattern pattern2 = pattern.compile(reg_2);
        Matcher matcher2 = pattern2.matcher(stringurl);
        while(matcher2.find()){
             urlDatas2.add(matcher2.group(2));
        }*/

        //标清和高清和2为1
        String reg3 = "(\"thumbURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg).*?(\"objURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg)";
        Pattern pattern3 = Pattern.compile(reg3);
        Matcher matcher3 = pattern3.matcher(stringurl);
        while(matcher3.find()){
            //Log.e(TAG, "parseFromRegest: "+matcher3.group(2));
            urlDatas.add(matcher3.group(2));
           // Log.e(TAG, "parseFromRegest: "+matcher3.group(7));
            urlDatas2.add(matcher3.group(7));
        }
    }


    private void initAdapter() {
        /*
        * 适配器
        * */
        //recycleView.setAdapter(new Photo_recycleView_Adapter(img_ids,getContext()));
        //布局管理器
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }


    private void initView() {
        recycleView = (RecyclerView)view.findViewById(R.id.recycleView);
    }
    /*
    * 分割线
    * */
    class ItemDecoration extends  RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left= DensityUtil.dip2px(getContext(),2);
            outRect.right= DensityUtil.dip2px(getContext(),2);
            outRect.bottom= DensityUtil.dip2px(getContext(),1);
            outRect.top= DensityUtil.dip2px(getContext(),1);
        }
    }
}
