package com.zhutingyu.learn.uidemo.multiRow

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.zhutingyu.learn.R

/**
 * @author Quiet Zhu
 * @date 2020-04-07
 * @description 不规则 多行多列控件 有空可优化继承ViewGroup(这个就要看心情了)
 */
abstract class BaseMultiRowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var layoutId: Int = -1
    private val lineLls = mutableListOf<LinearLayout>()
    private val itemViews = mutableListOf<View>()
    private var itemLineMargin = 0
    private var itemRowMargin = 0
    private var dataSize = 0
    private var rootViewW = 0
    private lateinit var currentLl: LinearLayout
    
    init {
        if (attrs != null) {
            val array = resources.obtainAttributes(attrs, R.styleable.BaseMultiRowView)
            layoutId = array.getResourceId(R.styleable.BaseMultiRowView_item_layout_resId, -1)
            itemLineMargin =
                array.getDimensionPixelSize(R.styleable.BaseMultiRowView_item_line_margin, 10)
            itemRowMargin =
                array.getDimensionPixelSize(R.styleable.BaseMultiRowView_item_row_margin, 10)
            
            array.recycle()
        }
        
        orientation = VERTICAL
        refresh()
    }
    
    protected fun refresh() {
        post {
            dataSize = getDataSize()
            if (dataSize > 0) {
                rootViewW = measuredWidth
                clean()
                
                currentLl = createLineLl(false)
                if (layoutId != -1) {
                    createViews()
                } else {
                    throw Exception("请设置item 布局资源")
                }
            }
            
        }
    }
    
    
    private fun createViews() {
        createItemViews()
        addItemViews()
        addLine()
    }
    
    private fun addItemViews() {
        var lineHoldEmpty = 0 //当前行所占空间
        var emptypace = rootViewW
        for (i in 0 until itemViews.size) {
            val view: View? = currentLl.getChildAt(currentLl.childCount - 1)
            if (view != null) {
                lineHoldEmpty += view.measuredWidth + itemLineMargin
                emptypace = rootViewW - lineHoldEmpty
            }
            if (itemViews[i].measuredWidth > emptypace) {
                currentLl = createLineLl(true)
                lineHoldEmpty = 0
                emptypace = rootViewW
            } else if (itemViews[i].measuredWidth == emptypace) {
                (itemViews[i].layoutParams as LayoutParams).setMargins(0, 0, 0, 0)
            }
            
            currentLl.addView(itemViews[i])
        }
    }
    
    
    //生成每一项的view
    private fun createItemViews() {
        for (i in 0 until dataSize) {
            val itemView = LayoutInflater.from(context).inflate(layoutId, null)
            bindItemData(i, itemView)
            val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            val h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            itemView.measure(w, h)
            itemViews.add(itemView)
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, itemRowMargin, 0)
                itemView.layoutParams = this
            }
            
        }
    }
    
    //添加每一行
    private fun addLine() {
        for (i in 0 until lineLls.size) {
            addView(lineLls[i])
        }
    }
    
    // 生成每行的的线性布局
    private fun createLineLl(haveMargin: Boolean): LinearLayout {
        val line = LinearLayout(context)
        line.orientation = HORIZONTAL
        LayoutParams(rootViewW, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            if (haveMargin) {
                setMargins(0, itemLineMargin, 0, 0)
            }
            line.layoutParams = this
            
        }
        lineLls.add(line)
        return line
    }
    
    private fun clean() {
        lineLls.clear()
        itemViews.clear()
    }
    
    //得到数据的长度
    abstract fun getDataSize(): Int
    
    //绑定数据
    abstract fun bindItemData(position: Int, view: View)
    
}