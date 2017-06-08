package com.ityingli.www.mynews.Util;

import android.content.Context;

/**
 * Created by Administrator on 2017/5/18.
 */

public class DensityUtil {

    public static int dip2px(Context context,float dpvalue){
       float scale =  context.getResources().getDisplayMetrics().density;
       return (int)(dpvalue *scale+0.5f);
    }

    public static int px2dip(Context context ,float pxvalue){
        float scale  =context.getResources().getDisplayMetrics().density;
        return (int)(pxvalue/scale+0.5f);
    }

}
