package com.ityingli.www.mynews.Adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityingli.www.mynews.R;


/**
 * Created by Administrator on 2017/5/16.
 */

public class Top_ListViewAdapter extends BaseAdapter{
     /*
     * 模拟数据有十条
     * */
    int DatasNumber = 10;

    private Context mcontext;
    public Top_ListViewAdapter(Context context){
        mcontext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder holder = null;
        int currenttype = getItemViewType(position);
         if(view == null){
             holder = new MyHolder();
             view = View.inflate(mcontext,R.layout.listview_item_type1,null);
             holder.img = (ImageView) view.findViewById(R.id.img_type1);
             holder.title = (TextView) view.findViewById(R.id.title);
             holder.time = (TextView)view.findViewById(R.id.time_type1);
             holder.author_name = (TextView) view.findViewById(R.id.author_name_type1);
             view.setTag(holder);
         }else{
            holder = (MyHolder) view.getTag();
         }
       holder.img.setImageResource(R.drawable.test);
         holder.title.setText("标题");
         holder.time.setText("类别");
         holder.author_name.setText("作者名字");
         return view;
    }


    class MyHolder{
        ImageView img;
        TextView title;
        TextView time;
        TextView author_name;
    }
}
