package com.zhutingyu.learn.painDemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BezierSampleView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    val mPaint = Paint()
    val mStartPoint = Point()
    val mEndPoint = Point()
    val mControlPoint = Point()
    val mPath = Path()
    val bezierP = Path()


    init {
        mPaint.strokeWidth = 3f
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.GRAY

        mStartPoint.x = -200
        mStartPoint.y = 0

        mEndPoint.x = 200
        mEndPoint.y = 0

        mControlPoint.x = 0
        mControlPoint.y = 0
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.translate(width / 2f, height / 2f)

        canvas.drawCircle(mStartPoint.x.toFloat(), mStartPoint.y.toFloat(), 5f, mPaint)
        canvas.drawCircle(mEndPoint.x.toFloat(), mEndPoint.y.toFloat(), 5f, mPaint)
        canvas.drawCircle(mControlPoint.x.toFloat(), mControlPoint.y.toFloat(), 5f, mPaint)

        mPath.moveTo(mStartPoint.x.toFloat(), mStartPoint.y.toFloat())
        mPath.lineTo(mControlPoint.x.toFloat(), mControlPoint.y.toFloat())
        mPath.lineTo(mEndPoint.x.toFloat(), mEndPoint.y.toFloat())

        canvas.drawPath(mPath, mPaint)


        bezierP.moveTo(mStartPoint.x.toFloat(), mStartPoint.y.toFloat())
        bezierP.quadTo(
            mControlPoint.x.toFloat(), mControlPoint.y.toFloat(),
            mEndPoint.x.toFloat(), mEndPoint.y.toFloat()
        )
        mPaint.color = Color.RED
        canvas.drawPath(bezierP, mPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mControlPoint.x = (event.x - (width / 2f)).toInt()
        mControlPoint.y = (event.y - (height / 2f)).toInt()
        reset()
        return true
    }

    fun reset() {
        mPaint.color = Color.GRAY
        mPath.reset()
        bezierP.reset()
        invalidate()
    }

}
