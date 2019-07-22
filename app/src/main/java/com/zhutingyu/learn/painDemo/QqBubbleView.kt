package com.zhutingyu.learn.painDemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.PointFEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresApi
import com.zhutingyu.paint.R

class QqBubbleView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val STATUS_DEFALUT = 0
    private val STATUS_CONNTECT = 1
    private val STATUS_DISSMISS = 2
    private val STATE_BUBBLE_APART = 3
    private val STATE_BUBBLE_RESET = 4
    private val STATE_BUBBLE_BOOM = 5
    private var mCStatus = STATUS_DEFALUT

    private val mFixBubbleMaxR = 20f
    private var mFixBubbleR = mFixBubbleMaxR
    private val mMoveBubbleR = 30f

    var mFixBubbleRCenter = PointF()
    var mMoveBubbleCenter = PointF()
    var mFixBubbleRCutPointA = PointF()
    var mFixBubbleRCutPointB = PointF()

    var mMoveBubbleCutPointA = PointF()
    var mMoveBubbleCutPointB = PointF()

    val mBezierControlPoint = PointF()

    val mBezierPath = Path()
    val mBezierPaint = Paint()
    val mBubblePaint = Paint()

    var mTwoBubbleDis: Double = 0.0
    val mBoomRect = Rect()

    private val mBurstDrawablesArray =
        intArrayOf(R.mipmap.burst_1, R.mipmap.burst_2, R.mipmap.burst_3, R.mipmap.burst_4, R.mipmap.burst_5)
    private val mBoomBitmapArray = mutableListOf<Bitmap>()

    private var mCurDrawableIndex = -1

    init {
        mBezierPaint.color = Color.RED
        mBezierPaint.style = Paint.Style.FILL

//        mBezierPath.fillType = Path.FillType.INVERSE_EVEN_ODD

        mBubblePaint.color = Color.RED
        mBubblePaint.style = Paint.Style.FILL
        mBurstDrawablesArray.forEach {
            mBoomBitmapArray.add(BitmapFactory.decodeResource(resources, it))

        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mFixBubbleRCenter.x = w / 2f
        mFixBubbleRCenter.y = h / 2f

        mMoveBubbleCenter.x = w / 2f
        mMoveBubbleCenter.y = h / 2f

    }

    override fun onDraw(canvas: Canvas) {
        mBezierPath.reset()

        if (mCStatus == STATUS_CONNTECT) {
            canvas.drawCircle(mFixBubbleRCenter.x, mFixBubbleRCenter.y, mFixBubbleR, mBubblePaint)
            val sinCen = (mMoveBubbleCenter.y - mFixBubbleRCenter.y) / mTwoBubbleDis
            val cosCen = (mMoveBubbleCenter.x - mFixBubbleRCenter.x) / mTwoBubbleDis

            mMoveBubbleCutPointA.x = (mMoveBubbleCenter.x + sinCen * mMoveBubbleR).toFloat()
            mMoveBubbleCutPointA.y = (mMoveBubbleCenter.y - cosCen * mMoveBubbleR).toFloat()

            mMoveBubbleCutPointB.x = (mMoveBubbleCenter.x - sinCen * mMoveBubbleR).toFloat()
            mMoveBubbleCutPointB.y = (mMoveBubbleCenter.y + cosCen * mMoveBubbleR).toFloat()

            mFixBubbleRCutPointA.x = (mFixBubbleRCenter.x + sinCen * mFixBubbleR).toFloat()
            mFixBubbleRCutPointA.y = (mFixBubbleRCenter.y - cosCen * mFixBubbleR).toFloat()

            mFixBubbleRCutPointB.x = (mFixBubbleRCenter.x - sinCen * mFixBubbleR).toFloat()
            mFixBubbleRCutPointB.y = (mFixBubbleRCenter.y + cosCen * mFixBubbleR).toFloat()

            mBezierControlPoint.x = (mFixBubbleRCenter.x + (cosCen * (mTwoBubbleDis / 3))).toFloat()
            mBezierControlPoint.y = (mFixBubbleRCenter.y + (sinCen * (mTwoBubbleDis / 3))).toFloat()

            mBezierPath.moveTo(mFixBubbleRCutPointA.x, mFixBubbleRCutPointA.y)
            mBezierPath.lineTo(mFixBubbleRCutPointB.x, mFixBubbleRCutPointB.y)

            mBezierPath.quadTo(
                mBezierControlPoint.x,
                mBezierControlPoint.y,
                mMoveBubbleCutPointB.x,
                mMoveBubbleCutPointB.y
            )
            mBezierPath.lineTo(mMoveBubbleCutPointA.x, mMoveBubbleCutPointA.y)

            mBezierPath.quadTo(
                mBezierControlPoint.x,
                mBezierControlPoint.y,
                mFixBubbleRCutPointA.x,
                mFixBubbleRCutPointA.y
            )

            canvas.drawPath(mBezierPath, mBezierPaint)
        }

        if (mCStatus != STATUS_DISSMISS && mCStatus != STATE_BUBBLE_BOOM) {
            canvas.drawCircle(mMoveBubbleCenter.x, mMoveBubbleCenter.y, mMoveBubbleR, mBubblePaint)
        }

        if (mCStatus == STATE_BUBBLE_BOOM && mCurDrawableIndex in 0 until mBoomBitmapArray.size) {
            mBoomRect.set(
                (mMoveBubbleCenter.x - mMoveBubbleR).toInt()
                , (mMoveBubbleCenter.y - mMoveBubbleR).toInt()
                , (mMoveBubbleCenter.x + mMoveBubbleR).toInt()
                , (mMoveBubbleCenter.y + mMoveBubbleR).toInt()
            )
            canvas.drawBitmap(mBoomBitmapArray[mCurDrawableIndex], null, mBoomRect, mBubblePaint)
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mTwoBubbleDis = Math.hypot(
            (event.x - mFixBubbleRCenter.x).toDouble(),
            (event.y - mFixBubbleRCenter.y).toDouble()
        )
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if (mTwoBubbleDis <= mMoveBubbleR) {
                    mCStatus = STATUS_CONNTECT
                } else {
                    mCStatus = STATUS_DEFALUT
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (mCStatus != STATUS_DEFALUT) {
                    mMoveBubbleCenter.x = event.x
                    mMoveBubbleCenter.y = event.y
                    if (mTwoBubbleDis > 300f) {
                        mCStatus = STATE_BUBBLE_APART
                    } else {
                        mFixBubbleR = mFixBubbleMaxR - (mTwoBubbleDis / 20).toFloat()
                    }
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {

                if (mCStatus == STATUS_CONNTECT) {
                    startReset()
                } else if (mCStatus == STATE_BUBBLE_APART) {
                    if (mTwoBubbleDis < 200) {
                        startReset()
                    } else {
                        startBoom()
                    }
                }
//
            }

        }
        return true

    }

    private fun startBoom() {
        mCStatus = STATE_BUBBLE_BOOM
        val anim = ValueAnimator.ofInt(0, mBoomBitmapArray.size)
        anim.duration = 500
        anim.addUpdateListener {
            mCurDrawableIndex = it.animatedValue as Int
            invalidate()
        }
        anim.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startReset() {
        mCStatus = STATE_BUBBLE_RESET
        val anim = ValueAnimator.ofObject(
            PointFEvaluator()
            , PointF(mMoveBubbleCenter.x, mMoveBubbleCenter.y)
            , mFixBubbleRCenter
        )
        anim.interpolator = OvershootInterpolator(5f)
        anim.duration = 300
        anim.addUpdateListener {
            val pointF: PointF = it.animatedValue as PointF
            mMoveBubbleCenter = pointF
            invalidate()
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mCStatus = STATUS_DEFALUT
            }
        })
        anim.start()
    }
}