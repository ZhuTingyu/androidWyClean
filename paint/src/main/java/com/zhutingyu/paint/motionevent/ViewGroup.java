package com.zhutingyu.paint.motionevent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Quiet Zhu
 * @date: 2019-09-09
 * @description: View 的容器
 */
public class ViewGroup extends View {

    private View[] mChrilds;
    private List<View> mChildList = new ArrayList<>();
    private View[] mChildren;

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {
        if (view == null) {
            return;
        }
        mChildList.add(view);
        mChildren = (View[]) mChildList.toArray(new View[mChildList.size()]);
    }

    //接受分发的代码
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println(name + " dispatchTouchEvent ");
        boolean handled = false;
        boolean intercepted = onInterceptTouchEvent(event);
        int actionMasked = event.getActionMasked();

        if (!intercepted && actionMasked != MotionEvent.ACTION_CANCEL) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                for (int i = mChildren.length - 1; i >= 0; i--) {
                    View childe = mChildren[i];
                    if (!childe.isContainer(event.getX(), event.getY())) {
                        continue;
                    }
                    if (dispatchTransformedTouchEvent(event, childe)) {
                        handled = true;
                        break;
                    }
                }
            }
        }
        return handled;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private boolean dispatchTransformedTouchEvent(MotionEvent event, View children) {
        boolean handle = children.dispatchTouchEvent(event);
        return handle;
    }

    private static final class TouchTarget {
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock = new Object();
        public View child;//当前缓存的view
        public TouchTarget next;
        private static int sRecycledCount;

        public static TouchTarget obtain(View child) {
            TouchTarget target = null;
            return target;
        }
    }

    private String name;

    @Override
    public String toString() {
        return "" + name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
