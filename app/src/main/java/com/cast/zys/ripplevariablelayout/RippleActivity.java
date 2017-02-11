package com.cast.zys.ripplevariablelayout;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cast.zys.ripplelayout.RippleLayout;

public class RippleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        //初始化控件
        RippleLayout rippleLayout = (RippleLayout) findViewById(R.id.wave);

        //设置中心坐标
        rippleLayout.setRippleCenter(new Point(100, 100));
        //设置波浪颜色
        rippleLayout.setRippleColor(Color.RED);
        //设置波浪时长
        rippleLayout.setRippleInternal(2000);
        //开启波浪动画
        rippleLayout.startRipple();
    }
}
