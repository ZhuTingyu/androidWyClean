package com.zhutingyu.learn.painDemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class SearchPathDemoView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    View(context, attrs, defStyleAttr) {

    val mPaint = Paint()
    val mSearchPath = Path()
    val mCircelPath = Path()
    val mPathMeasure = PathMeasure()

    val mSearchPathPos = FloatArray(2)

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND

        mPaint.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        canvas.translate(width / 2f, height / 2f)
        mSearchPath.addCircle(0f, 0f, 100f, Path.Direction.CW)
        mSearchPath.moveTo(100f, 100f)
        mSearchPath.lineTo(200f, 200f)



        canvas.drawPath(mSearchPath, mPaint)
    }

}