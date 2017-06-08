package com.ityingli.www.mynews.Util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/5.
 */

public class parseUrlFromRegest {
    private static final String TAG = "parseUrlFromRegest";
    private StringBuffer stringbuffer = new StringBuffer();

    /*
    * 用于装url
    * */
    public List<String> urlDatas =  new ArrayList<>();

    /*
    * 通过该类使用正则表达式解释图片的uri，a
    * */
     /*
     * 获取数据
     * */

    public void getDatas(String  url_) {
        final String stringUrl  = url_;
        new Thread(){
             public void run(){
                try {
                    //url
                    URL url = new URL(stringUrl);
                    //打开连接
                    HttpURLConnection connect  = (HttpURLConnection)url.openConnection();
                    //设置请求方式
                    connect.setRequestMethod("GET");
                    //设置请求时长，超时
                    connect.setConnectTimeout(8000);
                    //设置读取的时长
                    connect.setReadTimeout(8000);
                    //获取连接的流
                    InputStream is = connect.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = null;
                    //获取内容
                    while((line  = br.readLine())!=null){
                        stringbuffer.append(line);
                    }

            /*
            * 获取到数据，然后就解释数据了
            * */
                 parseDatas();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }




 /*
 * 正则获取所需要的数据
 * */
    private List<String> parseDatas() {
        //正则
        String s = stringbuffer+"";
        String reg = "(http:|https:){1}(//){1}((?!\").)*?.jpg";
        Pattern pattern = Pattern.compile(reg);
        Matcher mat= pattern.matcher(s);
        urlDatas.clear();
        while(mat.find()){
            urlDatas.add(mat.group());
        }
        Log.e(TAG, "parseDatas: "+urlDatas.size() );
        return urlDatas;
    }

}
