package com.zhutingyu.learn.painDemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasDemoView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        canvas.drawRect(0f, 0f, 200f, 200f, paint)
//        val matrix = Matrix()
//        matrix.setScale(1.5f, 1.5f)
//        canvas.matrix = matrix
//        paint.color = Color.GRAY
//        canvas.drawRect(200f, 200f, 400f, 400f, paint)
        canvas.save()
        canvas.translate(200f, 200f)
        paint.color = Color.RED
        canvas.drawRect(0f, 0f, 200f, 200f, paint)
        canvas.restore()

        paint.color = Color.GREEN
        canvas.drawRect(0f, 0f, 200f, 200f, paint)

    }

}