package com.zhutingyu.paint.pingxinview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.nineoldandroids.view.ViewHelper;
import com.zhutingyu.paint.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quiet Zhu
 * @date 2019-10-08
 * @description
 */
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment> fragments;
    private ParallaxPagerAdapter adapter;

    private ImageView iv_man;

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    public ParallaxContainer(@NonNull Context context) {
        super(context);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(int... childIds) {
//根据布局文件数组，初始化所有的fragment
        fragments = new ArrayList<ParallaxFragment>();
        for (int i = 0; i < childIds.length; i++) {
            ParallaxFragment f = new ParallaxFragment();
            Bundle args = new Bundle();
            //Fragment中需要加载的布局文件id
            args.putInt("layoutId", childIds[i]);
            f.setArguments(args);
            fragments.add(f);
        }
        //实例化适配器
        AppCompatActivity activity = (AppCompatActivity) getContext();
//实例化ViewPager
        ViewPager vp = new ViewPager(getContext());
        vp.setId(R.id.parallax_pager);
        adapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(), fragments);
        vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //绑定
        vp.setAdapter(adapter);
        addView(vp, 0);
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ParallaxFragment inFragment = null;
        int rootW = getWidth();
        ParallaxFragment outFragment = null;
        try {
            outFragment = fragments.get(position - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inFragment = fragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outFragment != null) {
            List<View> outViews = outFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    //获取标签，从标签上获取所有的动画参数
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }

                    ViewHelper.setTranslationX(view, rootW - positionOffsetPixels * tag.xIn);
                    ViewHelper.setTranslationY(view, rootW - positionOffsetPixels * tag.yIn);
                }
            }
        }

        if (inFragment != null) {
            List<View> inView = inFragment.getParallaxViews();
            if (inView != null) {
                for (View view : inView) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    ViewHelper.setTranslationY(view, 0 - positionOffsetPixels * tag.yOut);
                    ViewHelper.setTranslationX(view, 0 - positionOffsetPixels * tag.xOut);
                }
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        if (position == adapter.getCount() - 1) {
            iv_man.setVisibility(INVISIBLE);
        }else{
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;

            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;

            default:
                break;
        }
    }
}
