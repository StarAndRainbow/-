package com.ityingli.www.mynews.View;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MyWebView  extends WebView{

    /*
   * 该类主要用来继承WebView，重写一下方法，让程序后台运行的时候不可加载js动画（浪费内存，电量）
   * */
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPause() {
        super.onPause();
        //设置js动画不加载
        this.getSettings().setJavaScriptEnabled(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        this.getSettings().setJavaScriptEnabled(true);
    }
}
