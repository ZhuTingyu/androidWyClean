package com.zhutingyu.learn.painDemo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zhutingyu.learn.R
import com.zhutingyu.paint.canvas.split.Ball

/**
 * 粒子爆裂
 */
class SplitView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic)
    val balls = mutableListOf<Ball>()
    var bitmapW: Int = 0
    var bitmapH = 0
    var d = 3f
    private val paint = Paint()
    private var animator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)

    init {

        animator.repeatCount = 100
        animator.duration = 1000
        animator.addUpdateListener { _ ->
            balls.forEach {
                it.x += it.vX
                it.y += it.vY

                it.vX += it.aX
                it.vY += it.aY
            }
            invalidate()
        }

        bitmapW = bitmap.width
        bitmapH = bitmap.height
        for (i in 0 until bitmapW) {
            for (j in 0 until bitmapH) {
                val ball = Ball()
                ball.x = (i * d) + (d / 2f)
                ball.y = (j * d) + (d / 2f)

                ball.vX = (Math.pow(-1.0, Math.ceil(Math.random() * 1000)) * 20.0 * Math.random()).toFloat()
                ball.vY = rangInt(-15, 20).toFloat()

                ball.aX = 0f
                ball.aY = 1f

                ball.color = bitmap.getPixel(i, j)
                balls.add(ball)
            }
        }
    }

    private fun rangInt(i: Int, j: Int): Int {
        val max = Math.max(i, j)
        val min = Math.min(i, j) - 1
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (min + Math.ceil(Math.random() * (max - min))).toInt()
    }

    override fun onDraw(canvas: Canvas) {
//        canvas.drawBitmap(bitmap, 500f, 500f, paint)
        canvas.translate(500f, 500f)
        balls.forEach {
            paint.color = it.color
            canvas.drawCircle(it.x, it.y, d, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            animator.start()
        }
        return super.onTouchEvent(event)
    }
}

