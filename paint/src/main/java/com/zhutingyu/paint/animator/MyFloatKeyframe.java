package com.zhutingyu.paint.animator;

/**
 * @author Quiet Zhu
 * @date 2019-09-22
 * @description 关键帧 保存着 某一时刻的具体状态 初始化的时候
 */
public class MyFloatKeyframe {
    private float mFraction;//百分比
    private Class mValueType = float.class;//值得类型
    private float mValue;//

    public MyFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
    }

    public float getmFraction() {
        return mFraction;
    }

    public void setmFraction(float mFraction) {
        this.mFraction = mFraction;
    }

    public Class getmValueType() {
        return mValueType;
    }

    public void setmValueType(Class mValueType) {
        this.mValueType = mValueType;
    }

    public float getmValue() {
        return mValue;
    }

    public void setmValue(float mValue) {
        this.mValue = mValue;
    }
}
