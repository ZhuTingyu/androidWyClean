package com.zhutingyu.learn.uidemo.svg

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path



/**
 * @author Quiet Zhu
 * @date 2020-04-15
 * @description
 */
class ProviceItem {
    constructor(path: Path?){
        this.path = path
    }
    var path: Path? = null
    
    /**
     * 绘制颜色
     */
    var drawColor: Int = 0
    
    fun drawItem(canvas: Canvas, paint: Paint, isSelect: Boolean) {
        if (isSelect) {
            //            绘制内部的颜色
            paint.clearShadowLayer()
            paint.setStrokeWidth(1f)
            paint.setStyle(Paint.Style.FILL)
            paint.setColor(drawColor)
            canvas.drawPath(path, paint)
            //            绘制边界
            
            paint.setStyle(Paint.Style.STROKE)
            val strokeColor = -0x2f170c
            paint.setColor(strokeColor)
            canvas.drawPath(path, paint)
        } else {
            
            paint.setStrokeWidth(2f)
            paint.setColor(Color.BLACK)
            paint.setStyle(Paint.Style.FILL)
            paint.setShadowLayer(8f, 0f, 0f, 0xffffff)
            canvas.drawPath(path, paint)
            
            //            绘制边界
            paint.clearShadowLayer()
            paint.setColor(drawColor)
            paint.setStyle(Paint.Style.FILL)
            paint.setStrokeWidth(2f)
            canvas.drawPath(path, paint)
        }
        
        
    }
}