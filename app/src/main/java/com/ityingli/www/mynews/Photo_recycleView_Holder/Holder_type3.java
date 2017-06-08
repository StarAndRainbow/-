package com.ityingli.www.mynews.Photo_recycleView_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type3;
import com.ityingli.www.mynews.R;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Holder_type3 extends  BaseHolder<Type3> {
    public ImageView type3_iv;
    public TextView type3_name;
    public TextView type3_desc;
    public Holder_type3(View itemView) {
        super(itemView);
        type3_iv = (ImageView)itemView.findViewById(R.id.type3_img);
        type3_name = (TextView) itemView.findViewById(R.id.type3_name);
        type3_desc = (TextView) itemView.findViewById(R.id.type3_desc);
    }

    @Override
    public void bind(Type3 typeitem) {
        type3_iv.setImageResource(typeitem.img_id);
        type3_name.setText(typeitem.name);
        type3_desc.setText(typeitem.desc);
    }
}
