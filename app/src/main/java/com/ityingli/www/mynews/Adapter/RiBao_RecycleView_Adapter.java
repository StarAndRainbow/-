package com.ityingli.www.mynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ityingli.www.mynews.Bean.RiBao_recycleView_item;
import com.ityingli.www.mynews.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */

public class RiBao_RecycleView_Adapter extends RecyclerView.Adapter<RiBao_RecycleView_Adapter.MyHolder> implements View.OnClickListener {

    List<RiBao_recycleView_item> datas;
    View topView;
    Context mcontext;
    private OnItemClick onItemClick;

    public RiBao_RecycleView_Adapter(List<RiBao_recycleView_item> datas, Context mcontext,View topView){
       this.datas = datas;
        this.mcontext = mcontext;
        this.topView = topView;
       // Toast.makeText(mcontext,"=="+topView,Toast.LENGTH_SHORT).show();
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            //TextView tv = new TextView(mcontext);
            //tv.setText("aaaaaaa");
            return new MyHolder(topView);
        }else {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.ribao_recycleview_item, parent, false);

            /*
            * 在onCreateViewHolder里面创建点击事件
            * */
            view.setOnClickListener(this);
            return new MyHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
         int type =getItemViewType(position);
        if(type==0){

        }else {
            holder.title_tv.setText(datas.get(position).getTitle());
            Glide.with(mcontext).load(datas.get(position).getImgUri()).into(holder.img_iv);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    static class MyHolder  extends RecyclerView.ViewHolder{
        TextView title_tv;
        ImageView img_iv;
        public MyHolder(View itemView) {
            super(itemView);
             title_tv = (TextView)itemView.findViewById(R.id.title_tv);
             img_iv= (ImageView)(ImageView)itemView.findViewById(R.id.img_iv);
        }
    }



    /*
    * 设置item点击事件的回调接口
    * */
    public void setOnItemClick(OnItemClick onitemClick){
        this.onItemClick = onitemClick;
    }



    public interface  OnItemClick{
        public void ItemClick(View view,int position);
    }


    /*
    * 在onCreateHolder里面创建view的点击事件，并且调用我们回调接口，处理要做的事情
    * */
    @Override
    public void onClick(View v) {
        /*
        * 调用getTag方法获取position
        * */
        if(onItemClick!=null) {
            onItemClick.ItemClick(v, (int) v.getTag());
        }
    }
}
