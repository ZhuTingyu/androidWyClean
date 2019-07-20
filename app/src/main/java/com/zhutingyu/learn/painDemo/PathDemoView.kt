package com.zhutingyu.learn.painDemo

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

/**
 * path 基本使用
 */
class PathDemoView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    val mPath = Path()
    val mPaint = Paint()
    val mSystemPaint = Paint()
    val mSystemPath = Path()
    var mCenterPoint = Point()

    init {
        mSystemPaint.color = Color.RED
        mSystemPaint.strokeWidth = 2f
        mSystemPaint.style = Paint.Style.STROKE

        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 4f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenterPoint.x = w / 2
        mCenterPoint.y = h / 2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mSystemPath.moveTo(mCenterPoint.x.toFloat(), 0f)
        mSystemPath.lineTo(mCenterPoint.x.toFloat(), height.toFloat())
        mSystemPath.moveTo(0f, mCenterPoint.y.toFloat())
        mSystemPath.lineTo(width.toFloat(), mCenterPoint.y.toFloat())
        canvas.drawPath(mSystemPath, mSystemPaint)
        canvas.translate(mCenterPoint.x.toFloat(), mCenterPoint.y.toFloat())

        mPath.moveTo(0f, 0f)
//        mPath.rLineTo(200f,200f)
//
//        mPath.addRect(0f,0f, 300f,300f,Path.Direction.CW)
//        mPath.addRoundRect(-200f, 200f,300f,  300f, 10f,20f, Path.Direction.CW)

//        mPath.addOval(-400f, -200f, 400f, 200f, Path.Direction.CW)
//        mPath.addCircle(0f,0f, 100f,Path.Direction.CW)
//        mPath.addArc(-200f, -200f, 200f, 200f, 0f, 200f)

//        mPath.arcTo(-200f, -200f, 200f, 200f, 0f, 200f, true)

//        mPath.addRect(0f,0f, 300f,300f,Path.Direction.CW)
//        mPath.setLastPoint(-200f, 200f)

        canvas.drawPath(mPath, mPaint)
    }
}