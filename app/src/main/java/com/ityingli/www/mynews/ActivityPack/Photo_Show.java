package com.ityingli.www.mynews.ActivityPack;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityingli.www.mynews.Adapter.Photo_recycleView_Adapter;
import com.ityingli.www.mynews.R;

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

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class Photo_Show extends Activity implements View.OnClickListener {

    private ImageView bg_iv;
    private TextView back_tv;
    private int bg_id;
    private RecyclerView recycleView;
    private List<String> urlDatas_1;
    private List<String> urlDatas_2;
    private String stringurl;
    final int MESSAGE_ONE = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_ONE:
                    //设置适配器
                    Log.e(TAG, "handleMessage:==== "+urlDatas_1 );
                    recycleView.setAdapter(new Photo_recycleView_Adapter(urlDatas_1,Photo_Show.this));
                    break;
            }
        }
    };
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo__show);
        bg_id = getIntent().getIntExtra("bg_id",R.drawable.e);
        url = getIntent().getStringExtra("url");
        initView();
        initEvent();
        setDatas();

        //从网络获取数据
        geNetWorktDatas(url);
    }


    /*
   * 获取网络图片
   * */
    private void geNetWorktDatas(String url) {
         /*
         * 连接网络访问数据
         * */
        OkHttpClient client = new OkHttpClient();
        Request.Builder build = new Request.Builder();
        Request request  = build.get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+"联网失败" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stringurl = response.body().string();
                Log.e(TAG, "onResponse: "+stringurl );
                parseFromRegest(stringurl);
                handler.sendEmptyMessage(MESSAGE_ONE);
            }
        });
    }

    /*
    * 使用正则表达式解释返回的数据
    * */
    private void parseFromRegest(String stringurl) {
        urlDatas_1 = new ArrayList<>();
        urlDatas_2 = new ArrayList<>();
        String reg3 = "(\"thumbURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg).*?(\"objURL\":\"){1}((http:|https:){1}(//){1}((?!\").)*?.jpg)";
        Pattern pattern3 = Pattern.compile(reg3);
        Matcher matcher3 = pattern3.matcher(stringurl);
        while(matcher3.find()){
            Log.e(TAG, "parseFromRegest22: "+matcher3.group(2));
            urlDatas_1.add(matcher3.group(2));
            Log.e(TAG, "parseFromRegest22: "+matcher3.group(7));
            urlDatas_2.add(matcher3.group(7));
        }
    }




    private void setDatas() {
        /*
        * 设置数据
        * */
        bg_iv.setImageResource(bg_id);

    }


    private void initEvent() {
        back_tv.setOnClickListener(this);
    }

    private void initView() {
        bg_iv = (ImageView)findViewById(R.id.img_id);
        back_tv = (TextView)findViewById(R.id.back_id);
        recycleView = (RecyclerView)findViewById(R.id.recycleView_photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;
        }
    }
}
