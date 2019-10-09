package com.zhutingyu.paint.pingxinview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.zhutingyu.paint.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Quiet Zhu
 * @date 2019-10-08
 * @description
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    private ParallaxFragment fragment;

    protected ParallaxLayoutInflater(Context context) {
        super(context);
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        this.fragment = fragment;
        setFactory2(new ParallaxFactory());
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(newContext);
    }

    class ParallaxFactory implements Factory2 {

        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };

        int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out};

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            if (name.contains(".")) {
                view = createMyView(name, context, attrs);
            } else {
                for (String prefix : sClassPrefix) {
                    view = createMyView(prefix + name, context, attrs);
                    if (view != null) {
                        break;
                    }
                }
            }
            TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
            if (a != null && a.length() > 0) {
                //获取自定义属性的值
                ParallaxViewTag tag = new ParallaxViewTag();
                tag.alphaIn = a.getFloat(0, 0f);
                tag.alphaOut = a.getFloat(1, 0f);
                tag.xIn = a.getFloat(2, 0f);
                tag.xOut = a.getFloat(3, 0f);
                tag.yIn = a.getFloat(4, 0f);
                tag.yOut = a.getFloat(5, 0f);
                //index
                view.setTag(R.id.parallax_view_tag,tag);
            }
            a.recycle();
            fragment.getParallaxViews().add(view);
            return view;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }

    private View createMyView(String s, Context context, AttributeSet attrs) {
        try {
            Class clazz = Class.forName(s);
            Constructor<View> constructor = clazz.getConstructor(Context.class, AttributeSet.class);
            return constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
