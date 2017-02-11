package com.cast.zys.ripplelayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * ${END}
 * <p>
 * author by song-2017/2/11.14:09
 */

public class RippleLayout extends FrameLayout {

    private int internal = 2000;//时间间隔
    private int endRadius;
    int RippleColor = Color.parseColor("#FF0000");

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //重新开始动画
            startRipple();
        }
    };

    public RippleLayout(Context context) {
        this(context, null);
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 定义方法 初始化
     */
    private void init() {
        //获取屏幕的宽度作为结束半径
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        endRadius = windowManager.getDefaultDisplay().getWidth();
    }

    public void setRippleInternal(int internal) {
        this.internal = internal;
    }

    public void setEndRadius(int endRadius) {
        this.endRadius = endRadius;
    }

    public void setRippleColor(int RippleColor) {
        this.RippleColor = RippleColor;
    }

    /**
     * 定义方法 开始动画
     */
    public void startRipple() {
        addRippleView();
        //发送延时间隔
        handler.sendEmptyMessageDelayed(0, internal);
    }

    private Point center;

    /**
     * 设置圆心
     *
     * @param center
     */
    public void setRippleCenter(Point center) {
        this.center = center;
    }

    private void addRippleView() {
        RippleView rippleView = new RippleView(getContext());
        rippleView.setRadiusRange(0, endRadius);
        if (center != null) {
            rippleView.setRippleCenter(center);
        }
        rippleView.setRippleColor(RippleColor);

        rippleView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //设置监听当动画完成后 删除当前动画对象 重新创建对象 开启动画
        rippleView.getViewTreeObserver().addOnGlobalLayoutListener(new OnRippleViewLayoutListener(rippleView));
        this.addView(rippleView);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
        removeAllViews();
    }
}
