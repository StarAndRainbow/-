package com.ityingli.www.mynews.Photo_recycleView_Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/6/5.
 */

public  abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void  bind( T dataModel);
}
