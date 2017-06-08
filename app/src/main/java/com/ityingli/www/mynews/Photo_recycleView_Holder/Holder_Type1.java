package com.ityingli.www.mynews.Photo_recycleView_Holder;

import android.view.View;
import android.widget.TextView;

import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type1;
import com.ityingli.www.mynews.R;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Holder_Type1 extends BaseHolder<Type1> {
    TextView classification_name;
    TextView most;
    public Holder_Type1(View itemView) {
        super(itemView);
        classification_name = (TextView) itemView.findViewById(R.id.classification_name);
        most = (TextView) itemView.findViewById(R.id.most);
    }

    @Override
    public void bind(Type1 typeItem) {
        classification_name.setText(typeItem.textname);
        most.setText(typeItem.most);
    }

}
