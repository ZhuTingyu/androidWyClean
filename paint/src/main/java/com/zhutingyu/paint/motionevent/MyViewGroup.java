package com.zhutingyu.paint.motionevent;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Quiet Zhu
 * @date: 2019-09-09
 * @description: View 的容器
 */
public class MyViewGroup extends MyView {

    private MyView[] mChrilds;
    private List<MyView> mChildList = new ArrayList<>();
    private MyView[] mChildren;
    private TouchTarget mFirstTouchTarget;

    public MyViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(MyView view) {
        if (view == null) {
            return;
        }
        mChildList.add(view);
        mChildren = (MyView[]) mChildList.toArray(new MyView[mChildList.size()]);
    }

    //接受分发的代码
    public boolean dispatchTouchEvent(MyMotionEvent event) {
        System.out.println(name + " dispatchTouchEvent ");
        boolean handled = false;
        boolean intercepted = onInterceptTouchEvent(event);
        int actionMasked = event.getActionMasked();
        TouchTarget newTouchTarget = null;
        if (!intercepted && actionMasked != MyMotionEvent.ACTION_CANCEL) {
            if (actionMasked == MyMotionEvent.ACTION_DOWN) {
                for (int i = mChildren.length - 1; i >= 0; i--) {
                    MyView childe = mChildren[i];
                    if (!childe.isContainer(event.getX(), event.getY())) {
                        continue;
                    }
                    if (dispatchTransformedTouchEvent(event, childe)) {
                        handled = true;
                        newTouchTarget = addTouchTarget(childe);
                        break;
                    }
                }

                if (newTouchTarget == null) {
                    dispatchTransformedTouchEvent(event, null);
                }
            }
        }
        return handled;
    }

    public boolean onInterceptTouchEvent(MyMotionEvent ev) {
        return false;
    }

    private boolean dispatchTransformedTouchEvent(MyMotionEvent event, @Nullable MyView children) {
        boolean handle = false;
        if (children != null) {
            handle = children.dispatchTouchEvent(event);
        } else {
            handle = super.dispatchTouchEvent(event);
        }
        return handle;
    }

    private TouchTarget addTouchTarget(MyView view) {
        final TouchTarget target = TouchTarget.obtain(view);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }

    private static final class TouchTarget {
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock = new Object();
        private static int sRecycledCount;
        public MyView child;//当前缓存的view
        public TouchTarget next;

        public static TouchTarget obtain(MyView child) {
            TouchTarget target = null;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                    sRecycleBin = target.next;
                    sRecycledCount--;
                    target.next = null;
                }
            }
            target.child = child;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("已经被回收过了");
            }
            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                }

            }
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
