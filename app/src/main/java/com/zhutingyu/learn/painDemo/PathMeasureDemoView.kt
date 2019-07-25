package com.zhutingyu.learn.painDemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zhutingyu.paint.R

class PathMeasureDemoView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val mMatrix = Matrix()
    private val pos = FloatArray(2)
    private val tan = FloatArray(2)
    private val mPath = Path()
    private var mFloat: Float = 0.toFloat()

    private val mPaint = Paint()
    private val mLinePaint = Paint() //坐标系
    private val mBitmap: Bitmap

    val measure = PathMeasure()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 4f

        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.color = Color.RED
        mLinePaint.strokeWidth = 6f

        //缩小图片
        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.arrow, options)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), mLinePaint)
        canvas.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(), height.toFloat(), mLinePaint)

        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())

        val path = Path()
//        path.run {
//            moveTo(0f, 0f)
//            lineTo(0f,200f)
//            lineTo(200f, 200f)
//            lineTo(200f, 0f)
//        }
//
//        measure.setPath(path, false) //会影响测量长度
//
//        canvas.drawPath(path, mPaint)
//
//        val newPath = Path()
//        measure.getSegment(200f, 400f, newPath, true)

//        canvas.drawPath(newPath, mLinePaint)

//        path.addRect(-200f, 200f, 200f, 200f, Path.Direction.CCW)
//        path.addRect(-100f, 100f, 100f, 100f, Path.Direction.CCW)
//
//        val me1 = PathMeasure(path, false)
//        Log.e("tag", me1.length.toString())
//        me1.nextContour()
//        Log.e("tag", me1.length.toString())
        path.addCircle(0f, 0f, 200f, Path.Direction.CW)
        measure.setPath(path, false)
        canvas.drawPath(path, mPaint)

        Log.e("TAG", measure.getPosTan(measure.length / 8, pos, tan).toString())
        Log.e("TAG", "onDraw: pos[0]=" + pos[0] + ";pos[1]=" + pos[1])
        Log.e("TAG", "onDraw: tan[0]=" + tan[0] + ";tan[1]=" + tan[1])
////        canvas.drawBitmap()
//        mMatrix.reset()
//        val d = Math.atan2(tan[1].toDouble() , tan[0].toDouble()) * 180.0 / Math.PI
//        mMatrix.postRotate(
//            d.toFloat(), (mBitmap.width / 2).toFloat(), (mBitmap.height / 2).toFloat()
//        )
//        mMatrix.postTranslate(pos[0] - mBitmap.width / 2, pos[1] - mBitmap.height / 2)

        measure.getMatrix(measure.length / 8, mMatrix,
            PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG)

        canvas.drawBitmap(mBitmap, mMatrix, mPaint)

    }
}