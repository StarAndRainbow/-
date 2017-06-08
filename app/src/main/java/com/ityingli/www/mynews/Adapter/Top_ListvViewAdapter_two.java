package com.ityingli.www.mynews.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ityingli.www.mynews.Bean.homePager_topitem;
import com.ityingli.www.mynews.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class Top_ListvViewAdapter_two extends BaseAdapter {
    private List<homePager_topitem> top_datas;
    private Context mcontenxt;
  //  private LayoutInflater minflate;

    public Top_ListvViewAdapter_two(Context mcontenxt,List<homePager_topitem> top_datas){
        this.mcontenxt = mcontenxt;
       // minflate = LayoutInflater.from(mcontenxt);
        this.top_datas = top_datas;
    }

    @Override
    public int getViewTypeCount() {
        return homePager_topitem.TYPECOUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return top_datas.get(position).type;
    }

    @Override
    public int getCount() {
        return top_datas.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       MyHolder_type1 holder1 = null;
       MyHolder_type2 holder2 = null;
       int type = getItemViewType(position);

        if(convertView ==null){
            switch (type){
                case 0:
                   // convertView = minflate.inflate(R.layout.listview_item_type1,parent,false);
                    convertView = View.inflate(mcontenxt,R.layout.listview_item_type1,null);
                    holder1 = new MyHolder_type1();
                    holder1.img_type1 = (ImageView) convertView.findViewById(R.id.img_type1);
                    holder1.title_type1 = (TextView) convertView.findViewById(R.id.title_type1);
                    holder1.time_type1 = (TextView) convertView.findViewById(R.id.time_type1);
                    holder1.author_name_type1 = (TextView) convertView.findViewById(R.id.author_name_type1);
                    convertView.setTag(holder1);
                    break;
                case 1:
                    //convertView = minflate.inflate(R.layout.listview_item_type3,parent,false);
                    convertView = View.inflate(mcontenxt,R.layout.listview_item_type3,null);
                    holder2  = new MyHolder_type2();
                    holder2.img_type2 = (ImageView) convertView.findViewById(R.id.imag1_type2);
                    holder2.img2_type2 = (ImageView) convertView.findViewById(R.id.img2_type2);
                    holder2.img3_type2 = (ImageView) convertView.findViewById(R.id.img3_type2);
                    holder2.title_type2 = (TextView) convertView.findViewById(R.id.title_type2);
                    holder2.time_type2 = (TextView) convertView.findViewById(R.id.time_type2);
                    holder2.author_name_type2 = (TextView) convertView.findViewById(R.id.author_name_type2);
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        }else{
            switch (type){
                case 0:
                    holder1 = (MyHolder_type1)convertView.getTag();
                    break;
                case 1:
                    holder2 = (MyHolder_type2) convertView.getTag();
                    break;
                default:
                    break;
            }
        }

         switch (type){
             case 0:
                 /*
                 * 改用glide设置图片
                 * */
                 //holder1.img_type1.setImageResource(R.drawable.test);
                 Glide.with(mcontenxt).load(top_datas.get(position).thumbnail_pic_s).into(holder1.img_type1);
                 holder1.title_type1.setText(top_datas.get(position).title);
                 holder1.time_type1.setText(top_datas.get(position).date);
                 holder1.author_name_type1.setText(top_datas.get(position).author_name);
                 break;
             case 1:
                 Glide.with(mcontenxt).load(top_datas.get(position).thumbnail_pic_s).into(holder2.img_type2);
                 Glide.with(mcontenxt).load(top_datas.get(position).thumbnail_pic_s02).into(holder2.img2_type2);
                 Glide.with(mcontenxt).load(top_datas.get(position).thumbnail_pic_s03).into(holder2.img3_type2);
                 holder2.title_type2.setText(top_datas.get(position).title);
                 holder2.time_type2.setText(top_datas.get(position).date);
                 holder2.author_name_type2.setText(top_datas.get(position).author_name);
                 break;
         }

        return convertView;
    }

    class  MyHolder_type1{
        ImageView img_type1;
        TextView title_type1;
        TextView time_type1;
        TextView author_name_type1;
    }

    class  MyHolder_type2{
        ImageView img_type2;
        ImageView img2_type2;
        ImageView img3_type2;

        TextView title_type2;
        TextView time_type2;
        TextView author_name_type2;
    }
}
