package com.ityingli.www.mynews.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ityingli.www.mynews.R;

/**
 * Created by Administrator on 2017/5/17.
 */
public class TopListView extends ListView implements AbsListView.OnScrollListener{
    private Context mcontext;
    private View listView_footer;
    /*
    * 设置滚动的时候的参数
    * */
    int lastItem;
    int mtotalItemCount;
    OnLoadingFooter onLoadingFooter;
    /*
    * ListView footer的控件
    * */
    private ProgressBar footer_progressBar;
    private TextView footer_textview;
    private View listView_head;
    private int height;
    private int scrollState;
    private int firstVisibleItem;
    private boolean isRemark;
     int state;
    final int RELESE = 2 ;
    final int REFLASHING = 3;
    final int PULL = 1;
    final int NONE =0;
    private int startY;
    private HeadPUllReflash headPUllReflash;
    private ProgressBar head_Progreassbar;
    private TextView head_tv;


    public TopListView(Context context) {
        this(context,null);
    }

    public TopListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mcontext = context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        listView_footer  =layoutInflater.inflate(R.layout.top_listview_footer,null);
        footer_progressBar = (ProgressBar) listView_footer.findViewById(R.id.footer_progress);
        footer_textview = (TextView) listView_footer.findViewById(R.id.footer_text);
        this.addFooterView(listView_footer);
        listView_footer.setVisibility(GONE);
        this.setOnScrollListener(this);



        //1添加头布局，下拉刷新
        listView_head = layoutInflater.inflate(R.layout.top_listview_head,null);
        this.addHeaderView(listView_head);
        //2隐藏头部，这里不可以直接设置gone（否则拖出来的时候，整个出现），paddingTop可以解决这个问题
        float headPaddingTop = 0;
       // int  heignt = listView_head.getMeasuredHeight();  拿不到高度
        //Toast.makeText(mcontext, ""+heignt, Toast.LENGTH_SHORT).show();  //0
        //3先通知父布局到底占多大位置
        measureView(listView_head);
        //4拿高度,设置内边距
        height = listView_head.getMeasuredHeight();
        setHeadPadding(height);
    }

    private void measureView(View listView_head) {
        ViewGroup.LayoutParams p = listView_head.getLayoutParams();
        if(p==null){
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int tempHeight = p.height;
        if(tempHeight>0){
            //如果高度不是空，那么就填从这个布局
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else{
            height = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        listView_head.measure(width,height);
    }

    private void setHeadPadding(float headPaddingTop) {
              listView_head.setPadding(listView_head.getPaddingLeft(),-(int)headPaddingTop,listView_head.getPaddingRight(),listView_head.getPaddingBottom());
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
          if(lastItem == mtotalItemCount && scrollState == SCROLL_STATE_IDLE){
              listView_footer.setVisibility(VISIBLE);
              onLoadingFooter.loadingFooter();
          }

          //6记录当前的换
        this.scrollState = scrollState;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem +visibleItemCount;
        this.mtotalItemCount = totalItemCount;

        /*
        * 5记录第一个可以看到的item
        * */
        this.firstVisibleItem = firstVisibleItem;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0){
                    isRemark = true;
                    startY= (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(state==RELESE){
                    state=REFLASHING;
                    reflashViewByState();
                    //加载最新数据,调用刷新的接口
                   headPUllReflash.pullRefalsh();
                }
                else if(state==PULL){
                   // state=NONE;
                    //isRemark = false;
                   // reflashViewByState();
                    //如果拖没拖到顶部，那么就不用调用更新接口，并且把顶部设置隐藏
                    reflashComplete();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;

        }
        return super.onTouchEvent(ev);
    }



    private void topPadding(int topPadding){
        listView_head.setPadding(listView_head.getPaddingLeft(), topPadding, listView_head.getPaddingRight(), listView_head.getPaddingBottom());
    }


    /*
     * 判断移动过程中的操作
      */
    private void onMove(MotionEvent ev){
        if(!isRemark){
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY-startY;
        int topPadding= space-height;

        switch(state){
            case NONE:
                if(space>0)
                {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(space>height+30 && scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if(space<height+30 ){
                    state = PULL;
                    reflashViewByState();
                }else if(space<=0){
                    state = NONE;
                    reflashViewByState();
                    isRemark = false;
                }
                break;
            case REFLASHING:
                break;
        }

    }
    /**
     * 根据当前状态改变界面显示
     */
    private void reflashViewByState() {
        head_Progreassbar = (ProgressBar) listView_head.findViewById(R.id.head_progreassbar);
        head_tv = (TextView) listView_head.findViewById(R.id.head_text);
        switch(state){
            case NONE:
                head_tv.setText("");
                head_Progreassbar.setVisibility(GONE);
                break;
            case PULL:
                head_tv.setText("下拉刷新");
                head_Progreassbar.setVisibility(GONE);
                break;
            case RELESE:
                head_tv.setText("松手刷新");
                head_Progreassbar.setVisibility(GONE);
                break;
            case REFLASHING:
                head_tv.setText("正在刷新");
                head_Progreassbar.setVisibility(VISIBLE);
                break;
        }
    }


    /**
     * 获取完数据
     */
    public void reflashComplete(){
        state = NONE;
        isRemark = false;
        reflashViewByState();
        this.topPadding(-height);
    }


    public void setonLoadingFooter(OnLoadingFooter onLoadingFooter){
          this.onLoadingFooter = onLoadingFooter;
    }

    public interface  OnLoadingFooter{
        void  loadingFooter();
    }

    public void loadComplete(){
        listView_footer.setVisibility(GONE);
    }



    public void noMostDatas(){
       footer_progressBar.setVisibility(GONE);
        footer_textview.setText("暂没更多数据");
    }



    //下拉刷新的下拉接口
    public interface  HeadPUllReflash{
         public void pullRefalsh();
    }

    public void setHeadPUllReflash(HeadPUllReflash headPullReflash){
          this.headPUllReflash = headPullReflash;
    }

}
