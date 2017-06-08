package com.ityingli.www.mynews.Pager;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ityingli.www.mynews.Adapter.RiBao_RecycleView_Adapter;
import com.ityingli.www.mynews.Adapter.ViewPager_carousel_Adapter;
import com.ityingli.www.mynews.Bean.RiBao_Viewpager;
import com.ityingli.www.mynews.Bean.RiBao_recycleView_item;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.Util.DensityUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RiBaoFragment extends Fragment {
    String TAG="RiBaoFragment";
    private View view;
    private Toolbar toolbar;
    private ViewPager viewPager_carousele;
    private LinearLayout dot_ll;
    private int selectingPointIndex = 0 ;
    final  int  MESSAGE_ONE  =1;
    final  int  MESSAGE_TWO  =2;
    final int MESSAGE_THREE =3;
    private boolean isRuning = true;
    private ArrayList<RiBao_Viewpager> imgDatas;
    private ArrayList<RiBao_recycleView_item> recycleView_Datas;
    private RecyclerView recyclerView;
    private Context mcontext;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_ONE:
                    viewPager_carousele.setCurrentItem((viewPager_carousele.getCurrentItem()+1)%imgDatas.size());
                    if(isRuning){
                        handler.sendEmptyMessageDelayed(MESSAGE_ONE,3000);
                    }
                    break;
                case MESSAGE_TWO:
                    setViewPagerDatas();
                    break;
                case MESSAGE_THREE:
                    recyclerView.setAdapter(new RiBao_RecycleView_Adapter(recycleView_Datas,getContext(),null));
                    break;
            }
        }
    };
    private ArrayList<String> titleDatas;
    private ArrayList<String> imgUriDatas;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ribao_fragment,container,false);
        initView();
         initviewPager_carousele();
         handler.sendEmptyMessageDelayed(MESSAGE_ONE,3000);
         initRecycleView();
        /*
        * RecycleView设置滑动事件
        * */
       //recyclerView.setOnScrollListener(new recycleViweOnScrollView(mcontext));



        return view;
    }

    /*
    * 初始化RecycleView
    * */
    private void initRecycleView() {
        //适配器
        //recyclerView.setAdapter(new RiBao_RecycleView_Adapter(titleDatas,imgUriDatas,getContext()));
        //布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加分隔线
        recyclerView.addItemDecoration(new MyRecycleViewIteemDecoration());

    }


    /*
    * 联网获取数据
    * */
    public void getDatas() {
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
                getViewPagerJsonData(string);
                ///获取完数据后发一条消息，设置适配器
                handler.sendEmptyMessage(MESSAGE_TWO);


                getRecycleViewDatas(string);
                handler.sendEmptyMessage(MESSAGE_THREE);
            }
        });


    }

    /*
    *获取recycleView要用到的信息
    * */
    private void getRecycleViewDatas(String string) {
        recycleView_Datas  =new ArrayList<>();
        try {
            JSONObject Object  = new JSONObject(string);
            JSONArray jsonArray = Object.getJSONArray("stories");
            for(int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String imgUri = (String)jsonObject.getJSONArray("images").get(0);
                String title = (String)jsonObject.get("title");
                int id = (int)jsonObject.get("id");
               RiBao_recycleView_item item = new RiBao_recycleView_item(imgUri,title,id);
                recycleView_Datas.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                imgDatas.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //轮播图
    private void initviewPager_carousele() {
        //数据源
        imgDatas  = new ArrayList<>();
        //获取数据
        getDatas();
        //设置点

        //这里的5是对应的5张图片
        for(int i = 0 ;i<5;i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.dot_select);
            LinearLayout.LayoutParams lp  = new  LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(),5),DensityUtil.dip2px(getContext(),5));
            lp.leftMargin = DensityUtil.dip2px(getContext(),5);
            iv.setLayoutParams(lp);
            dot_ll.addView(iv);
        }
        ImageView iv = (ImageView)dot_ll.getChildAt(selectingPointIndex );
        iv.setPressed(true);

    }
       /*
       * 把数据设置到轮播图ViewPager
       * */
    private void  setViewPagerDatas() {

        viewPager_carousele = (ViewPager)view.findViewById(R.id.viewpager_carousel);
        viewPager_carousele.setAdapter(new ViewPager_carousel_Adapter(imgDatas,getContext()));

        viewPager_carousele.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ImageView selectingPoint = (ImageView)dot_ll.getChildAt(position);
                selectingPoint.setPressed(true);
                ImageView iv2 = (ImageView)dot_ll.getChildAt(selectingPointIndex);
                iv2.setPressed(false);
                selectingPointIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {
        dot_ll  = (LinearLayout)view.findViewById(R.id.dot_ll);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRuning = false;
        handler.removeMessages(MESSAGE_ONE);
        if(view!=null) {
         //   ((ViewGroup) view.getParent()).removeView(view);
        }
    }



    //RecycleView的分割线
    class MyRecycleViewIteemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = DensityUtil.dip2px(getContext(),5);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mcontext = null;
    }





    class recycleViweOnScrollView extends RecyclerView.OnScrollListener{
        Context mcontext;
        recycleViweOnScrollView(Context mcontext){
            this.mcontext = mcontext;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(dy>0){
              Toast.makeText(mcontext,"往下走",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mcontext,"往上走",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
