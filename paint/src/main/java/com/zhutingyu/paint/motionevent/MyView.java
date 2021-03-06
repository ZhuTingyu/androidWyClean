package com.zhutingyu.paint.motionevent;

import com.zhutingyu.paint.motionevent.litener.OnClickListener;
import com.zhutingyu.paint.motionevent.litener.OnTouchListener;

/**
 * @author: Quiet Zhu
 * @date: 2019-09-09
 * @description: 手写控件View
 */
public class MyView {
    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener onClickListener;

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyView() {
    }

    public MyView(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isContainer(int x, int y) {
        return x >= left && x < right && y >= top && y < bottom;
    }

    //接受分发的代码
    public boolean dispatchTouchEvent(MyMotionEvent event) {
        System.out.println(" view  dispatchTouchEvent ");
//        消费
        boolean result = false;

        if (mOnTouchListener != null && mOnTouchListener.onTouch(this, event)) {
            result = true;
        }
        if (!result && onTouchEvent(event)) {
            result = true;
        }
        return result;
    }

    protected boolean onTouchEvent(MyMotionEvent event) {
        if (onClickListener != null) {
            onClickListener.onClick(this);
            return true;
        }
        return false;
    }
}
