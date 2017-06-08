package com.ityingli.www.mynews.ActivityPack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ityingli.www.mynews.Pager.URLPake.RibaoFragment_Uri;
import com.ityingli.www.mynews.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RiBao_Item_ContentActivity extends AppCompatActivity implements View.OnClickListener {
    String  TAG = "RiBao";
    private ImageView back;
    private ImageView collect;
    private ImageView comment;
    private TextView commentNumber;
    private ImageView praise;
    private TextView praiseNumber;
    private ImageView img;
    private TextView title;
    private WebView textContent;
    private String imgUri;
    private String body;
    private String titleText;
    final int MESSAGE_ONE = 1;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg)
            switch (msg.what){
                case MESSAGE_ONE:
                    title.setText(titleText);
                    //img.setImageResource();
                    Glide.with(RiBao_Item_ContentActivity.this).load(imgUri).into(img);
                    textContent.loadDataWithBaseURL("about:blank",getNewContent(body),"text/html","utf-8",null);

                    /*loadDataWithBaseURL(String baseUrl,
                                String data,
                                String mimeType,
                                String encoding,
                                String historyUrl)*/
                    break;
            }
        }
    };
    private int andid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_bao__item__content);
        /*
        * 初始化控件
        * */
        findViews();
        /*
        * 获得id
        * */
        andid = getIntent().getIntExtra("useId",1111);
        Log.e(TAG, "onCreate: "+andid );
        /*
        * 获取需要的数据
        * */
        getUseDatas();
        /*
        * 初始化点击事件
        * */
        back.setOnClickListener(this);

    }

    private void getUseDatas() {
        OkHttpClient okhttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        /*
        * 通过uri+id获取数据
        * */
        getDatasFromUrl(okhttpClient, builder);
    }

    private void getDatasFromUrl(OkHttpClient okhttpClient, Request.Builder builder) {
        Request request  = builder.get().url(RibaoFragment_Uri.contentUril+(andid+"")).build();
        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string  = response.body().string();
                Log.e(TAG, "onResponse:"+string);
                Log.e(TAG, "onResponse:"+RibaoFragment_Uri.contentUril+(andid+""));
                parseJsonGetDatas(string);
            }
        });
    }

    /*
    * 通过json数据获取Datas
    * */
    private void parseJsonGetDatas(String string) {
        try {
           JSONObject object =  new JSONObject(string);
          imgUri = (String) object.get("image");
          titleText = (String) object.get("title");
          body = (String)object.get("body");
          Log.e(TAG, "onResponse:"+"aaaa");
          handler.sendEmptyMessage(MESSAGE_ONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-05-31 10:36:21 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        back = (ImageView)findViewById( R.id.back );
        collect = (ImageView)findViewById( R.id.collect );
        comment = (ImageView)findViewById( R.id.comment );
        commentNumber = (TextView)findViewById( R.id.comment_number );
        praise = (ImageView)findViewById( R.id.praise );
        praiseNumber = (TextView)findViewById( R.id.praise_number );
        img = (ImageView)findViewById( R.id.img );
        title = (TextView)findViewById( R.id.title );
        textContent = (WebView)findViewById( R.id.text_content );
    }

    /*
    * 点击事件
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    /*
    * 让webView加载的html的图片适配屏幕的方法
    * */
    private String getNewContent(String htmltext){
        int i = 0;
        Document doc=Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            if(i==0){

            }else {
                element.attr("width", "100%").attr("height", "auto");
            }
            i = i+1;
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }
}







