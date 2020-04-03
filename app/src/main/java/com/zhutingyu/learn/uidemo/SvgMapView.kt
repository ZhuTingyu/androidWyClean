package com.zhutingyu.learn.uidemo

import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.zhutingyu.learn.R
import java.lang.Exception
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
//    private val itemList: List<ProviceItem>? = null
    private var paint: Paint = Paint()
    //    private val select: ProviceItem? = null
    private val totalRect: RectF? = null
    private val scale = 1.0f
    private val loadThread = object : Thread() {
        override fun run() {
            val inputString = context.resources.openRawResource(R.raw.china)
            val factory = DocumentBuilderFactory.newInstance()
            try {
                val builder = factory.newDocumentBuilder()
                val doc = builder.parse(inputString)
            } catch (error: Exception) {
            
            }
            
        }
    }
    init {
        paint.isAntiAlias = true
    }
    
}




