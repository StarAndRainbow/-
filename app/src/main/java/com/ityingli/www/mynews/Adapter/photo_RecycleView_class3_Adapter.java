package com.ityingli.www.mynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type1;
import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type2;
import com.ityingli.www.mynews.Photo_RecycleView_Item_Datas.Type3;
import com.ityingli.www.mynews.Photo_recycleView_Holder.BaseHolder;
import com.ityingli.www.mynews.Photo_recycleView_Holder.Holder_Type1;
import com.ityingli.www.mynews.Photo_recycleView_Holder.Holder_type2;
import com.ityingli.www.mynews.Photo_recycleView_Holder.Holder_type3;
import com.ityingli.www.mynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class photo_RecycleView_class3_Adapter  extends RecyclerView.Adapter<BaseHolder> implements View.OnClickListener{


    /*
    * 接口的对象
    * */

    SetItemClick setItemClick;

    /*
    * 分别对应三个不同的类型
    * */
     final int TYPE_ONE = 1;
     final int TYPE_TWO = 2;
     final int TYPE_THREE = 3;

    /*
    * 用于得到数据的三个集合
    * */
    List<Type1> list1;
    List<Type2> list2;
    List<Type3> list3;


    Context mcontext;
    LayoutInflater layoutInflater;
    public photo_RecycleView_class3_Adapter(Context mcontext){
        this.mcontext = mcontext;
        layoutInflater = LayoutInflater.from(mcontext);
    }






   public void addData(List<Type1> list1,List<Type2> list2, List<Type3> list3){
       this.list1 = list1;
       this.list2 = list2;
       this.list3 = list3;
       /*
       * 添加数据之后呢，也弄一个集合保存类型
       * */
       //addDataForType(TYPE_ONE,list1);
       //addDataForType(TYPE_TWO,list2);
       //addDataForType(TYPE_THREE,list3);
   }

   /*
   * 弄一个集合保存类型
   * */
   //保存类型
   public  List<Integer> types = new ArrayList<>();
    //保存类型的其实位置
    //public Map<Integer,Integer> mposint = new HashMap<>();
    public List<Integer> locations = new ArrayList<>();

    public void addDataForType(int type,int location) {
      //      mposint.put(type,types.size());    //保存一段数据的开始位置
      //  for(int i = 0 ;i<list.size();i++){
      //      types.add(type);
      //  }
      //这里我进行修改保存类型的修改，要每次加一条数据的时候，保存一次，保存一次类型，并且保存一次位置
      types.add(type);
      //并且保存该数据所在集合的位置
      locations.add(location);
    }




    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ONE:
                View view =layoutInflater.inflate(R.layout.layout_photo_typeitem_one,parent,false);
                return new Holder_Type1(view);

            case TYPE_TWO:
                View view2 = layoutInflater.inflate(R.layout.layout_photo_typeitem_two,parent,false);
                return new Holder_type2(view2);
            case TYPE_THREE:
                View view3 = layoutInflater.inflate(R.layout.layout_photo_typeitem_three,parent,false);
                view3.setOnClickListener(this);
                return new Holder_type3(view3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
      int type = getItemViewType(position);
        /*
        * 获取位置
        * */
        //int realPosition = position - mposint.get(type);
        switch (type){
            case TYPE_ONE:
                ((Holder_Type1)holder).bind(list1.get(locations.get(position)));
                break;
            case TYPE_TWO:
                ((Holder_type2)holder).bind(list2.get(locations.get(position)));
                break;
            case TYPE_THREE:
                ((Holder_type3)holder).bind(list3.get(locations.get(position)));
                break;
        }
        /*
        * 把位置设置成标记
        * */
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    /*
    * 实现点击事件
    * */

    @Override
    public void onClick(View v) {
       /*
       * 点击的时候调用接口
       * */
       if(setItemClick!=null){
       setItemClick.ItemClick(v,(int)v.getTag());
       }
    }


    public void setonItemClick(SetItemClick setItemClick){
        this.setItemClick = setItemClick;
    }


    public interface   SetItemClick{
       void  ItemClick(View view,int positon);
    }


}
