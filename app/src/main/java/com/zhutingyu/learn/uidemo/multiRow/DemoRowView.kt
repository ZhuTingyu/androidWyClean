package com.zhutingyu.learn.uidemo.multiRow

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.zhutingyu.learn.R

/**
 * @author Quiet Zhu
 * @date 2020-04-07
 * @description
 */
class DemoRowView(context: Context, attrs: AttributeSet) :
    BaseMultiRowView(context, attrs) {
    var data: List<String>? = null
    set(value) {
        field = value
        refresh()
    }
    override fun getDataSize(): Int {
        return data?.size ?: 0
    }
    
    override fun bindItemData(position: Int, view: View) {
        view.findViewById<TextView>(R.id.tvTitle).text = data?.get(position) ?: ""
    }
}