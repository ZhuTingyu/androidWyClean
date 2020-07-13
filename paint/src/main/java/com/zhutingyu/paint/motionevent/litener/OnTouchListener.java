package com.zhutingyu.paint.motionevent.litener;


import com.zhutingyu.paint.motionevent.MyMotionEvent;
import com.zhutingyu.paint.motionevent.MyView;

public interface OnTouchListener {
    boolean onTouch(MyView v, MyMotionEvent event);
}
