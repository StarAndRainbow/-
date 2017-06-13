package com.ityingli.www.mynews.ActivityPack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.ityingli.www.mynews.R;

/**
 * Created by Administrator on 2017/6/12.
 */

public class SpashActivity  extends Activity{


    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(SpashActivity.this,MainActivity.class);
            SpashActivity.this.finish();
            startActivity(intent);
        }
    };*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        /*
        * 设置两秒之后跳转界面
        * */
       // handler.sendEmptyMessageDelayed(1,2000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpashActivity.this,MainActivity.class);
                startActivity(intent);
                SpashActivity.this.finish();
            }
        },2000);
    }
}
