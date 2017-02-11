package com.cast.zys.ripplelayout;

import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * ${END}
 * <p>
 * author by song-2017/2/11.14:19
 */

public class OnRippleViewLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private RippleView rippleView;

    public OnRippleViewLayoutListener(RippleView rippleView) {
        this.rippleView = rippleView;
    }

    @Override
    public void onGlobalLayout() {
        rippleView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        rippleView.rippleAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewGroup parent = (ViewGroup) rippleView.getParent();
                parent.removeView(rippleView);
                rippleView = null;
            }
        }, rippleView.getDuration() + 5);
    }
}
