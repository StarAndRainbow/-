package com.ityingli.www.mynews.Photo_recycleView_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type2;
import com.ityingli.www.mynews.R;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Holder_type2  extends BaseHolder<Type2>{
    ImageView type2_imageView;
    TextView type2_tv;
    public Holder_type2(View itemView) {
        super(itemView);
        type2_imageView = (ImageView) itemView.findViewById(R.id.type2_imgView);
        type2_tv = (TextView) itemView.findViewById(R.id.type2_text);
    }


    @Override
    public void bind(Type2 typeitem) {
       type2_imageView.setImageResource(typeitem.imgid);
        type2_tv.setText(typeitem.img_Text);
    }
}
