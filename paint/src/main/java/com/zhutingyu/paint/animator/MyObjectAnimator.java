package com.zhutingyu.paint.animator;

import android.animation.TimeInterpolator;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author Quiet Zhu
 * @date 2019-09-22
 * @description 手写属性动画
 */
public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {
    private long mDuration = 0;
    private WeakReference<View> mTarget;
    private MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;
    private TimeInterpolator mInterpolator;
    private long mStartTime;
    private int mIndex;

    private MyObjectAnimator(View view, String propertyName, float... floats) {
        mTarget = new WeakReference<>(view);
        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, floats);
    }

    public static MyObjectAnimator ofFloat(View view, String propertyName, float... floats) {
        return new MyObjectAnimator(view, propertyName, floats);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void start() {
        myFloatPropertyValuesHolder.setupSetter(mTarget);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
    }

    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total = mDuration / 16;
        //执行的百分比
        float fraction = (mIndex++) / total;

        if (mInterpolator != null) {
            fraction = mInterpolator.getInterpolation(fraction);
        }

        if (mIndex >= total) {
            mIndex = 0;
        }

        myFloatPropertyValuesHolder.setAnimatedValue(mTarget.get(), fraction);

        return false;
    }
}
