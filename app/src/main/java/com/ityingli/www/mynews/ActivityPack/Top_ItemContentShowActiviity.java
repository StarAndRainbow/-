package com.ityingli.www.mynews.ActivityPack;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.View.MyWebView;

public class Top_ItemContentShowActiviity extends Activity{
    private MyWebView webView;
    private LinearLayout load_LinearLayout;
    private ProgressBar load_progressbar;
    private TextView load_textview;
    private TextView load_title;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_top__item_content_show_activiity);
        initView();
        initEvent();
    }

    private void initEvent() {
        webView.loadUrl(getIntent().getStringExtra("itemKey"));
        //webView的子类
        WebSettings webSettings = webView.getSettings();
        //设置webView可以加载js
        webSettings.setJavaScriptEnabled(true);



       webView.setWebViewClient(new WebViewClient(){
           /*
            * 强制webView中加载
            * */
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               view.loadUrl(request.getUrl().toString());//在webView中加载
               return true;
           }

           /*
           * 设置刚开始加载网页的时候显示进度条
           * */

           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
               load_LinearLayout.setVisibility(View.VISIBLE);
           }




           /*
           * 设置完成的时候隐藏进度条
           * */

           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               load_LinearLayout.setVisibility(View.GONE);
           }



           /*
           * 加载出错
           * */
           @Override
           public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
               super.onReceivedError(view, request, error);
               load_progressbar.setVisibility(View.GONE);
               load_textview.setText("加载出错");
           }

       });

        webView.setWebChromeClient(new WebChromeClient(){
            /*
            * 设置显示进度
            * */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress<70){     //其实可以填<100的，但是为了更好看，到70我就让这个布局隐藏
                    load_textview.setText(newProgress+"%");
                }else{
                    load_LinearLayout.setVisibility(View.GONE);
                }
            }

            /*
            * 设置显示标题
            * */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                load_title.setText(title);
            }
        });
}





    /*
    * 设置点击回车的时候，可以退到上一个历史网页，否则回整个页面退出
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
              webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
       webView = (MyWebView) findViewById(R.id.webView);
        load_LinearLayout = (LinearLayout)findViewById(R.id.load_ll);
        load_progressbar = (ProgressBar)findViewById(R.id.load_progressbar);
        load_textview = (TextView)findViewById(R.id.load_textview);
        load_title = (TextView)findViewById(R.id.load_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出这个界面的时候清理缓存
        webView.clearCache(true);
    }



}
