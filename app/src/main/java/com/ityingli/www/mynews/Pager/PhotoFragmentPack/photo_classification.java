package com.ityingli.www.mynews.Pager.PhotoFragmentPack;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.ActivityPack.Photo_Show;
import com.ityingli.www.mynews.Adapter.photo_RecycleView_class3_Adapter;
import com.ityingli.www.mynews.Pager.URLPake.PhoToFragmentUri;
import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type1;
import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type2;
import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type3;
import com.ityingli.www.mynews.R;
import com.ityingli.www.mynews.Util.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class photo_classification extends Fragment {
    private static final String TAG = "photo_classification";
    private View view;
    PhoToFragmentUri urls = new PhoToFragmentUri();

    /*
    * RecyclewView
    * */
    private RecyclerView recycleView;
    /*
    * recycleView适配器
    * */
    private photo_RecycleView_class3_Adapter recycleViewAdapter;
    /*
    * 优雅的使用RecycleView的不同布局
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photo_classification, container, false);
        //1初始化数据源
        initView();
        //2定义要用到的布局
        //3定义要用到的数据实体类
        //4定义好ViewHolder
        //5初始化数据
        //6创建适配其
        recycleViewAdapter = new photo_RecycleView_class3_Adapter(getContext());
        //先创建适配器，因为下面我使用适配器传递数据
        initDatas();
        /*
        * 7设置适配器
        * */
        recycleView.setAdapter(recycleViewAdapter);
        /*
        * 8设置布局管理器
        * */
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if(recycleView.getAdapter().getItemViewType(position)==1){
                     return gridLayoutManager.getSpanCount();
                }else if(recycleView.getAdapter().getItemViewType(position)==2){
                    return gridLayoutManager.getSpanCount();
                }else if(recycleView.getAdapter().getItemViewType(position)==3){
                    return 1;
                }else{
                    return 2;//否则（其实这里不可能运行到）,随便返回一个即可
                }
            }
        });
        /*
        * 设置
        * */
        recycleView.setLayoutManager(gridLayoutManager);

        /*
        * 图片的顺序
        * */
        final int imgid[] = {R.drawable.erciyuan,R.drawable.wangzhe,R.drawable.chiyuye,
                R.mipmap.ic_launcher,R.drawable.dongman,R.drawable.qianyuqianxun,R.drawable.dongmnashouhui,R.drawable.dongmantouxiang,
                R.drawable.bizhi,R.mipmap.ic_launcher,R.drawable.oumei,R.drawable.xinggan,R.drawable.rihan,R.drawable.qingxun
        };

        /*
        *给RecycleView设置回调点击事件
        * */
        recycleViewAdapter.setonItemClick(new photo_RecycleView_class3_Adapter.SetItemClick() {
            @Override
            public void ItemClick(View view, int positon) {
                Log.e(TAG, "ItemClick: "+positon );
                Intent intent = new Intent(getContext(), Photo_Show.class);
                intent.putExtra("bg_id",imgid[positon]);
                String url = null;
                 switch (positon){
                     case 1:
                         url = urls.wangzhe;
                         break;
                     case 2:
                         url = urls.chiyuye;
                         break;
                     case 4:
                         url=urls.dongman;
                         break;
                     case 5:
                         url = urls.qianyuqianxun;
                         break;
                     case 6:
                         url = urls.dongmanshouhui;
                         break;
                     case 7:
                         url = urls.dongmantouxian;
                         break;
                     case 10:
                         url= urls.oumei;
                         break;
                     case 11:
                         url = urls.xinggan;
                         break;
                     case 12:
                         url = urls.rihan;
                         break;
                     case 13:
                         url = urls.qingchun;
                         break;
                 }
                 intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        /*
        * 设置分割线
        * */

        recycleView.addItemDecoration(new ItemDecoration());

        return view;
    }

    /*
    * 初始化数据
    * */
    private void initDatas() {
        List<Type1> list1 = new ArrayList<>();
        List<Type2> list2 = new ArrayList<>();
        List<Type3> list3 = new ArrayList<>();

        list2.add(new Type2(R.drawable.erciyuan,"热门"));
        recycleViewAdapter.addDataForType(2,list2.size()-1);
        list3.add(new Type3(R.drawable.wangzhe,"王者荣耀","王者荣耀图片赏析"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.chiyuye,"赤羽业","赤羽业图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);


        list1.add(new Type1("动漫","更多"));
        recycleViewAdapter.addDataForType(1,list1.size()-1);
        list3.add(new Type3(R.drawable.dongman,"动漫","动漫图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.qianyuqianxun,"千与千寻","千与千寻图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.dongmnashouhui,"动漫手绘","海量动漫手绘欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.dongmantouxiang,"动漫头像","海量动漫头像欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);

        list2.add(new Type2(R.drawable.bizhi,"精选图片壁纸"));
        recycleViewAdapter.addDataForType(2,list2.size()-1);
        list1.add(new Type1("美女壁纸","更多"));
        recycleViewAdapter.addDataForType(1,list1.size()-1);
        list3.add(new Type3(R.drawable.oumei,"欧美","美女图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.xinggan,"性感","美女图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.rihan,"日韩","美女图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        list3.add(new Type3(R.drawable.qingxun,"青纯","美女图片欣赏"));
        recycleViewAdapter.addDataForType(3,list3.size()-1);
        recycleViewAdapter.addData(list1,list2,list3);
        recycleViewAdapter.notifyDataSetChanged();
    }


    private void initView() {
        //初始化View
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
    }


    /*
   * 分割线
   * */
    class ItemDecoration extends  RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left= DensityUtil.dip2px(getContext(),2);
            outRect.right= DensityUtil.dip2px(getContext(),2);
            outRect.bottom= DensityUtil.dip2px(getContext(),1);
            outRect.top= DensityUtil.dip2px(getContext(),1);
        }
    }
}
