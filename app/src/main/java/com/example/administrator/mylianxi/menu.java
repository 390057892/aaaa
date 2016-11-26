package com.example.administrator.mylianxi;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/26.
 */
public class menu extends HorizontalScrollView{

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mcontext;
    private int mMenuWidth;
    //屏幕宽度
    private int mScreenWidth;
    //滑动后右侧宽度
    private int mMenuRightPadding=50;

    private boolean once;

    private boolean isopen;

    //未使用自定义方法时
    public menu(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }
//当使用了自定义控件时。会调用此构造方法
    public menu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,
                 R.styleable.menu,defStyleAttr,0);
        int n=a.getIndexCount();

        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.menu_RightPadding:
                    mMenuRightPadding=a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,
                            context.getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();

        WindowManager wm= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth=metrics.widthPixels;
        //将dp转换为px
//        mMenuRightPadding= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,
//                context.getResources().getDisplayMetrics());
    }

    public menu(Context context) {
        this(context,null);
    }



    //设置子View的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mcontext = (ViewGroup) mWapper.getChildAt(1);

            mMenuWidth= mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mcontext.getLayoutParams().width = mScreenWidth;
            once=true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //通过设置偏移量，现将menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        super.onLayout(changed, l, t, r, b);
        if(changed) {
            this.scrollTo(mMenuWidth, 0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX=getScrollX();
                if(scrollX>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isopen=false;
                }else {
                    this.smoothScrollTo(0,0);
                    isopen=true;
                }
                return true;

        }

        return super.onTouchEvent(ev);
    }

    public void openMenu(){
        if(isopen)return;
        this.smoothScrollTo(0,0);
        isopen=true;
    }
    public void closeMenu(){
        if(!isopen)return;
        this.smoothScrollTo(mMenuWidth,0);
        isopen=false;
    }
    public void toggle(){
        if(isopen){
            closeMenu();
        }else {
            openMenu();
        }

    }
}
