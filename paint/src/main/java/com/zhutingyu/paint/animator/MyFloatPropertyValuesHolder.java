package com.zhutingyu.paint.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Quiet Zhu
 * @date 2019-09-22
 * @description 关键帧管理器
 */
public class MyFloatPropertyValuesHolder {
    //    属性名
    String mPropertyName;
    //float
    Class mValueType;
    MyKeyframeSet mKeyframes;
    Method mSetter = null;

    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframes = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target) {
        char f = Character.toUpperCase(mPropertyName.charAt(0));
        String set = mPropertyName.substring(1);
        String methodName = "set" + f + set;
        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setAnimatedValue(View target, float fraction) {
        Object value = mKeyframes.getValue(fraction);
        try {
//            View.setScaleX(101px)
            mSetter.invoke(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
