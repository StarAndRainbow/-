package com.ityingli.www.mynews.Pager;
//(http:|https:){1}(//){1}.*?.jpg    正则表达式抓取图片网址
////解释出数据之后，发现这个正则式是有漏网之鱼的
//这里优化，一个网址中间是没有双引号的  (http:|https:){1}(//){1}((?!").)*?.jpg
//注解：.*？   代表0个或者多个非\n的任意字符(并且是非贪婪模式)
//对于正则表达式中?!的理解：我目前的理解是不想被捕获


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoFragment extends Fragment {
   String TAG = "VideoFragment";
   private View view;
    StringBuffer stringbuffer = new StringBuffer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_video,container,false);
        /*
        * 初始化控件
        * */
        initView();
        /*
        *通过http连接网路获取网页源码
        * */
        getDatas();
        /*
        * 用正则表达式抓取源码中的图片
        * */
        //parseDatas();
        return view;
    }

    /*
    * 正则获取所需要的数据
    * */
    private void parseDatas() {
        //正则
        //String reg = "(http:|https:){1}(//){1}.*?.jpg";
        String reg = "(http:|https:){1}(//){1}((?!\").)*?.jpg";
        Pattern pattern = Pattern.compile(reg);
        Matcher mat= pattern.matcher(stringbuffer);
       while(mat.find()){
           Log.e(TAG, "parseDatas:"+mat.group());
       }
    }



    /*
     * 获取数据
     * */
    private void getDatas() {
        new Thread(){
            public void run(){
        String stringUrl  = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=index&fr=&hs=0&xthttps=111111&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E7%BE%8E%E5%A5%B3&oq=%E7%BE%8E%E5%A5%B3&rsp=-1";
        //String stringUrl  = "http://open.lovebizhi.com/pengyou.php#";
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

           // Log.e("aaa", "getDatas: "+stringbuffer );
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

    private void initView() {
        TabLayout tabLayout= (TabLayout)view.findViewById(R.id.tablayout);
        String[] itemDatas = {"推荐","搞笑","视频","八卦","萌物","影视","涨姿势","美女","小品"};
        for(int i = 0 ;i<itemDatas.length;i++) {
            tabLayout.addTab(tabLayout.newTab().setText(itemDatas[i]));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*
        * 网络连接关闭
        * */

      /*  if(connect!=null){
            connect.disconnect();
        }*/
    }

}
