package com.zhutingyu.learn.uidemo.myrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import com.zhutingyu.learn.R


/**
 * @author Quiet Zhu
 * @date 2020/5/4
 * @description
 */
class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private var touchSlop = 0  //最小滑动距离
    
    private var mWidth = 0
    
    private var mHeight = 0
    private var heights: IntArray? = null
    
    //当前显示的View
    private val viewList = mutableListOf<View>()
    private var recycler: MyRecycler? = null
    
    //当前滑动的y值
    private var currentY = 0
    
    //行数
    private var rowCount = 0
    
    //view的第一行  是占内容的几行
    private var firstRow = 0
    
    //y偏移量
    private var mScrollY = 0
    
    //初始化  第一屏最慢
    private var needRelayout = false
    private var adapter: Adapter? = null
    
    init {
        val configuration = ViewConfiguration.get(context)
        touchSlop = configuration.scaledTouchSlop
        needRelayout = true
    }
    
    
    fun setAdapter(adapter: Adapter?) {
        this.adapter = adapter
        if (adapter != null) {
            recycler = MyRecycler(adapter.viewTypeCount)
            mScrollY = 0
            firstRow = 0
            needRelayout = true
            requestLayout() //1  onMeasure   2  onLayout
        }
    }
    
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (needRelayout || changed) {
            needRelayout = false
            viewList.clear()
            removeAllViews()
            if (adapter != null) {
                mWidth = r - l
                mHeight = b - t
                
                var left = 0
                var top = 0
                var right = 0
                var bottom = 0
                
                for (i in 0 until rowCount) {
                    if (top < mHeight) {
                        right = width
                        bottom = top + heights!![i]
                        val view = makeAndStep(i,0, top, right, bottom)
                        viewList.add(view!!)
                        top = bottom
                    } else {
                        break
                    }
                }
            }
        }
    }
    
    private fun makeAndStep(
        row: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): View? {
        val view  = obtainView(row, right - left, bottom - top)
        view.layout(left, top, right, bottom)
        return view
    }
    
    private fun obtainView(row: Int, width: Int, height: Int): View {
        //        key type
        val itemType = adapter!!.getItemViewType(row)
        //       取不到
        val reclyView = recycler!!.get(itemType)
        var view: View? = null
        if (reclyView == null) {
            view = adapter!!.onCreateViewHodler(row, this)
            if (view == null) {
                throw RuntimeException("onCreateViewHodler  必须填充布局")
            }
        } else {
            view = adapter!!.onBinderViewHodler(row, reclyView, this)
        }
        view!!.setTag(R.id.tag_type_view, itemType)
        view.measure(
            MeasureSpec.makeMeasureSpec(
                width,
                MeasureSpec.EXACTLY
            )
            ,
            MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY
            )
        )
        addView(view, 0)
        return view
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var h = 0
        adapter?.let {
            this.rowCount = it.count
            heights = intArrayOf(rowCount)
            for (i in heights!!.indices) {
                heights!![i] = adapter!!.getHeight(i)
            }
            
            val tmpH = sumArray(heights!!, 0, heights!!.size)
            h = heightSize.coerceAtMost(tmpH)
            setMeasuredDimension(widthSize, h)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    
    private fun sumArray(array: IntArray, firstIndex: Int, count: Int): Int {
        var count = count
        var sum = 0
        count += firstIndex
        for (i in firstIndex until count) {
            sum += array[i]
        }
        return sum
    }
    
    interface Adapter {
        fun onCreateViewHodler(
            position: Int,
            parent: ViewGroup?
        ): View?
        
        fun onBinderViewHodler(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View?
        
        //Item的类型
        fun getItemViewType(row: Int): Int
        
        //Item的类型数量
        val viewTypeCount: Int
        
        val count: Int
        
        fun getHeight(index: Int): Int
    }
}