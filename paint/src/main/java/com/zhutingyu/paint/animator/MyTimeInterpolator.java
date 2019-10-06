package com.zhutingyu.paint.animator;


import android.animation.TimeInterpolator;

/**
 * @author Quiet Zhu
 * @date 2019-10-06
 * @description
 */
public class MyTimeInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input * 4;
    }
}
