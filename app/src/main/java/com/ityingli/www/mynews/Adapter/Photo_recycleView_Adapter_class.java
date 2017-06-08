package com.ityingli.www.mynews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ityingli.www.mynews.R;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/6/5.
 */

public class Photo_recycleView_Adapter_class extends RecyclerView.Adapter<Photo_recycleView_Adapter_class.myholder> implements View.OnClickListener {
    private static final String TAG = "Photo_recycleView_Adapt";

    Context mcontext;
    List<String> imgurl;
    private float width;
    android.os.Handler handler;
    private float imgwidth;
    private float imgheight;
    private float scle;
    ItemClick itemClick;

    public Photo_recycleView_Adapter_class(List<String> imgs_url, Context mcontext){
        this.mcontext = mcontext;
        this.imgurl = imgs_url;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view_item = LayoutInflater.from(mcontext).inflate(R.layout.photo_recycleview_item,parent,false);
        myholder holder = new myholder(view_item);
        view_item.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myholder holder, final int position) {
        //用于点击事件返回位置
        holder.itemView.setTag(position);
        //获取屏幕的大少/2接近Item的宽度
        getwidth();
        /*
        *获取图片原来的高度
        * */
        //Glide.with(mcontext).load(imgurl.get(position)).override((int)500,(int)500).centerCrop().into(holder.iv);
        Glide.with(mcontext).load(imgurl.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                 imgwidth = bitmap.getWidth();
                 imgheight = bitmap.getHeight();
                if(imgwidth>width){
                    //大于宽度按比例缩小
                    scle = (width/imgwidth);
                }else if(imgwidth<width){
                    //小于宽度,图片放大
                    scle =(imgwidth/imgwidth);
                }
                /*
                * bitmap按比例缩放
                * */
                Matrix matrix = new Matrix();
                matrix.postScale(scle,scle);   //缩放的比例
                bitmap = Bitmap.createBitmap(bitmap,0,0,(int)imgwidth,(int)imgheight,matrix,true);
               /*
               * 获取新的bitmap的高度，设置item的高度就ok了
               * */
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                lp.height = bitmap.getHeight();
                holder.itemView.setLayoutParams(lp);
                //设置图片
                holder.iv.setImageBitmap(bitmap);

            }
        });
    }

    private void getImgWidth(final int position) {
        new Thread() {
            public float imgwidth;
            public float imgheight;

            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try
                {
                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(imgurl.get(position)).openStream(), null, options);

                    //图片的宽和高
                    imgwidth = options.outWidth;
                    imgheight = options.outHeight;
                    //根据图片的宽度设定高度
                    if(imgwidth>width){
                        //大于宽度按比例缩小
                        scle = (int)(imgwidth/width);
                        imgwidth = imgwidth/scle;
                        imgheight =imgheight/scle;
                    }else if(imgwidth<width){
                        //小于宽度,图片放大    //6/2=3   6/3=2
                        scle =(int)(width/imgwidth);
                        imgwidth =(width/imgwidth)*imgwidth;
                        imgheight = (width/imgwidth)*imgheight;
                    }
                    Log.e(TAG, "onBindViewHolder:图片的最终宽和高 " + imgwidth+"--"+imgheight);

                    /*
                    * 发一条信息用于设置的宽和高
                    * */
                    Message msg = Message.obtain();
                    Bundle bundler = new Bundle();
                    bundler.putFloat("width",imgwidth);
                    bundler.putFloat("height",imgheight);
                    msg.setData(bundler);
                    msg.what=1;
                    handler.sendMessage(msg);

                    /*
                    * 后来看到这样一个方法
                    * */
                    //path是图片的路径，这个glide用方法获得
                    /*BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile("path", options);// 这里你应该要替换(总之就是将图片转换为位图)
                    int width = options.outWidth;// 得到图片宽
                    int height = options.outHeight;// 得到图片高*/


                    //或者看看下面这个
                    /*单独加载Bitmap进行操作处理，根据你的需求可如下操作：
                    Glide.with(this)//activty
                            .load("image url")
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                    // Do something with bitmap here.
                                    bitmap.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                                    bitmap.getWidth();
                                }

                            });
                    */
                } catch (
                        IOException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getwidth() {
        WindowManager windowManager = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels/2;
        Log.e(TAG, "onBindViewHolder: "+width);
    }

    @Override
    public int getItemCount() {
        return imgurl.size();
    }




    class myholder extends RecyclerView.ViewHolder{
        private ImageView iv;
        public myholder(View itemView) {
            super(itemView);
            iv = (ImageView)itemView.findViewById(R.id.recycleView_item_iv_id);
        }
    }

    /*
    * item的点击事件
    * */


        @Override
        public void onClick(View v) {
            if(itemClick!=null) {
                this.itemClick.itemClick(v,(int)v.getTag());
            }
    }


    /*
    * 接收回调接口
    * */
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    /*
    * 回调接口
    * */
    public interface  ItemClick{
         void itemClick(View view, int position);
    }



}
