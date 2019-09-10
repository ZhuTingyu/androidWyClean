package com.zhutingyu.paint.motionevent.litener;


import com.zhutingyu.paint.motionevent.MotionEvent;
import com.zhutingyu.paint.motionevent.View;

public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
