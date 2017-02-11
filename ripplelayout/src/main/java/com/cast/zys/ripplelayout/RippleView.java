package com.cast.zys.ripplelayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class RippleView extends View {

    // 设置起始的时候的半径
    private int startRadius = 0;
    // 设置结束的时候的半径
    private int endRadius = 0;
    //设置变量保存当前的波浪半径
    private int radius = startRadius;
    //设置变量 设置圆心
    private Point center;
    //设置动画时长 默认为6s
    long duration = 6000;
    private int color = Color.parseColor("#FF0000");
    private Paint paint;
    private ValueAnimator animator;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        // 创建抗锯齿画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置画笔的默认颜色
        paint.setColor(color);
    }

    /**
     * 定义方法 设置画笔的颜色
     *
     * @param color
     */
    public void setRippleColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    /**
     * 定义方法 设置波纹范围
     *
     * @param startRadius
     * @param endRadius
     */
    public void setRadiusRange(int startRadius, int endRadius) {
        this.startRadius = startRadius;
        this.endRadius = endRadius;
    }

    /**
     * 定义方法 返回动画时长
     *
     * @return
     */
    public long getDuration() {
        return duration;
    }

    /**
     * 定义方法 设置动画时长
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 定义方法 设置波浪中心坐标
     *
     * @param center
     */
    public void setRippleCenter(Point center) {
        this.center = center;
    }

    /**
     * 重写方法 初始化
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置半径
        if (endRadius == 0) {
            endRadius = (int) Math.sqrt(Math.pow(getMeasuredWidth(), 2) + Math.pow(getMeasuredHeight(), 2));
        }
        //设置圆心
        if (center == null) {
            center = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }
        //开始画圆
        canvas.drawCircle(center.x, center.y, radius, paint);
    }

    /**
     * 定义方法 开始执行水波动画
     */
    public void rippleAnim() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(startRadius, endRadius);
            //设置插值器
            animator.setInterpolator(new LinearInterpolator());
            //设置动画的监听
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取更新的值
                    radius = (int) valueAnimator.getAnimatedValue();
                    //设置隐藏动画
                    setAlpha(1 - valueAnimator.getAnimatedFraction());
                    //刷新布局
                    invalidate();
                }
            });
            //设置动画时长
            animator.setDuration(duration);
        }
        //开启动画
        animator.start();
    }

    /**
     * 重写方法 防止过度占用资源
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        startRadius = 0;
        endRadius = 0;
        animator.cancel();
    }

    /**
     * 定义方法 取消动画
     */
    public void stopRipple() {
        animator.cancel();
    }
}
