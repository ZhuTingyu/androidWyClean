package com.zhutingyu.paint.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.util.Arrays;
import java.util.List;

/**
 * @author Quiet Zhu
 * @date 2019-09-22
 * @description 关键帧集合
 */
class MyKeyframeSet {
    MyFloatKeyframe mFirstKeyframe;
    List<MyFloatKeyframe> mKeyframes;
    TypeEvaluator mEvaluator;

    private MyKeyframeSet(MyFloatKeyframe... floatKeyframes) {
        mKeyframes = Arrays.asList(floatKeyframes);
        mFirstKeyframe = floatKeyframes[0];
        mEvaluator = new FloatEvaluator();
    }

    public static MyKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        MyFloatKeyframe[] myKeyframeSets = new MyFloatKeyframe[values.length];
        myKeyframeSets[0] = new MyFloatKeyframe(0, values[0]);
        for (int i = 1; i < numKeyframes; i++) {
            myKeyframeSets[i] = new MyFloatKeyframe((float) i / numKeyframes - 1, values[i]);
        }
        return new MyKeyframeSet(myKeyframeSets);
    }

    public Object getValue(float faction) {
        MyFloatKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 0, len = mKeyframes.size(); i < len; i++) {
            MyFloatKeyframe nextFrame = mKeyframes.get(i);
            if (faction < nextFrame.getmFraction()) {
                return mEvaluator.evaluate(faction, prevKeyframe.getmValue(), nextFrame.getmValue());
            }
            prevKeyframe = nextFrame;
        }

        return null;
    }
}
