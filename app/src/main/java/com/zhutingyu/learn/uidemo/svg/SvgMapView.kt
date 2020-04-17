package com.zhutingyu.learn.uidemo.svg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.PathParser
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory


/**
 * @author Quiet Zhu
 * @date 2020-03-27
 * @description  通过加载svg文件绘制不规则图形
 */
class SvgMapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    private val colorArray = intArrayOf(-0xdc6429, -0xcf561b, -0x7f340f, -0x1)
    private val itemList: MutableList<ProviceItem> = mutableListOf()
    private var paint: Paint = Paint()
    private val select: ProviceItem? = null
    private var totalRect: RectF? = null
    private val scale = 1.0f
    private val loadThread = object : Thread() {
        override fun run() {
            val inputString = context.resources.openRawResource(com.zhutingyu.learn.R.raw.china)
            val factory = DocumentBuilderFactory.newInstance()
            try {
                val builder = factory.newDocumentBuilder()
                val doc = builder.parse(inputString)
                val rootElements = doc.documentElement
                val nodeList = rootElements.getElementsByTagName("path")
                val list = arrayListOf<ProviceItem>()
                var left = -1f
                var right = -1f
                var top = -1f
                var bottom = -1f
                for (i in 0 until nodeList.length) {
                    val element = nodeList.item(i) as Element
                    val pathData = element.getAttribute("android:pathData")
                    val path = PathParser.createPathFromPathData(pathData)
                    val proviceItem = ProviceItem(path)
                    proviceItem.drawColor = (colorArray[i % 4])
                    val rect = RectF()
                    path.computeBounds(rect, true)
                    left = if (left == -1f) rect.left else left.coerceAtMost(rect.left)
                    right = if (right == -1f) rect.right else right.coerceAtLeast(rect.right)
                    top = if (top == -1f) rect.top else top.coerceAtMost(rect.top)
                    bottom = if (bottom == -1f) rect.bottom else bottom.coerceAtLeast(rect.bottom)
                    itemList.add(proviceItem)
                }
                totalRect = RectF(left, top, right, bottom)
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    requestLayout()
                    invalidate()
                }
            } catch (error: Exception) {
            
            }
            
        }
    }
    
    init {
        paint.isAntiAlias = true
        loadThread.start()
    }
    
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (itemList != null) {
            canvas.save()
            canvas.scale(scale, scale)
            for (proviceItem in itemList) {
                if (proviceItem != select) {
                    proviceItem.drawItem(canvas, paint, false)
                } else {
                    select.drawItem(canvas, paint, true)
                }
            }
        }
    }
}




