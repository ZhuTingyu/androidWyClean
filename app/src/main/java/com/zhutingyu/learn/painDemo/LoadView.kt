package com.zhutingyu.learn.painDemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import com.zhutingyu.learn.R

class LoadView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    //旋转大圆的半径
    private val mBallRadius = 90f
    //6个小球的半径
    //背景色
    private val mBallPaint = Paint()
    private var mBallColors: IntArray
    var mCenterX: Float = 0f
    var mCenterY: Float = 0f
    var mScreenC: Float = 0f//屏幕对角线的一半
    var mCStatus: DrawStatus? = null
    var mBallD: Float = 18.0F //球体直径
    var mDisBallToCenter = mBallRadius

    var mOneBallToXAngle: Float = 0.0F
    private var rotateAngle: Float
    private var expandRadius = 0f
    private var expandPaint = Paint()

    init {
        mBallPaint.isAntiAlias = true
        mBallPaint.style = Paint.Style.FILL

        expandPaint.isAntiAlias = true
        expandPaint.color = Color.YELLOW
        expandPaint.style = Paint.Style.STROKE

        mBallColors = context?.resources?.getIntArray(R.array.splash_circle_colors)!!
        rotateAngle = (Math.PI * 2 / mBallColors.size).toFloat()//球之间的角度

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenterX = w / 2f
        mCenterY = h / 2f
        mScreenC = Math.hypot(w.toDouble(), h.toDouble()).toFloat() / 2f
    }

    override fun onDraw(canvas: Canvas) {
        if (mCStatus == null) {
            mCStatus = RotateStatus()
        }
        mCStatus!!.draw(canvas)
    }


    fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }

    fun drawBall(canvas: Canvas) {
        mBallColors.forEachIndexed { index, it ->
            mBallPaint.color = it
            canvas.drawCircle(
                mCenterX + mDisBallToCenter * Math.cos(mOneBallToXAngle.toDouble() + (rotateAngle * index)).toFloat()
                , mCenterY + mDisBallToCenter * Math.sin(mOneBallToXAngle.toDouble() + (rotateAngle * index)).toFloat()
                , mBallD
                , mBallPaint
            )
        }

    }

    interface DrawStatus {
        fun draw(canvas: Canvas)
    }

    //1.画6个圆
    private inner class RotateStatus : DrawStatus {
        private var anim: ValueAnimator = ValueAnimator.ofFloat(0.toFloat(), (Math.PI * 2).toFloat())

        constructor() {
            anim.interpolator = LinearInterpolator()
            anim.duration = 1000
            anim.repeatCount = 1
            anim.addUpdateListener {
                mOneBallToXAngle = it.animatedValue as Float
                invalidate()
            }
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mCStatus = MerginStatus()
                    invalidate()
                }
            })
            anim.start()
        }

        override fun draw(canvas: Canvas) {
            drawBackground(canvas)
            drawBall(canvas)
        }

    }

    //2.散开聚拢
    private inner class MerginStatus : DrawStatus {
        private var anim: ValueAnimator = ValueAnimator.ofFloat(mBallD, mDisBallToCenter)

        constructor() {
            anim.interpolator = OvershootInterpolator(20f)
            anim.duration = 1000
            anim.addUpdateListener {
                mDisBallToCenter = it.animatedValue as Float
                Log.e("MerginStatus", mDisBallToCenter.toString())
                invalidate()
            }
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mCStatus = ExpandState()
                    invalidate()
                }
            })
            anim.reverse()
        }

        override fun draw(canvas: Canvas) {
            drawBackground(canvas)
            drawBall(canvas)
        }
    }

    //3.画空心圆
    private inner class ExpandState : DrawStatus {
        private var anim: ValueAnimator = ValueAnimator.ofFloat(0F, mScreenC)
        constructor(){
            anim.interpolator = LinearInterpolator()
            anim.addUpdateListener {
                expandRadius = it.animatedValue as Float
                invalidate()
            }
            anim.start()
        }
        override fun draw(canvas: Canvas) {
            val strokeWidth = mScreenC - expandRadius
            val radius = strokeWidth / 2 + expandRadius
            expandPaint.strokeWidth = strokeWidth
            canvas.drawCircle(mCenterX, mCenterY, radius, expandPaint)
        }

    }

}