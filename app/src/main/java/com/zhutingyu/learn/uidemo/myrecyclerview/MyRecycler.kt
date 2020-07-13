package com.zhutingyu.learn.uidemo.myrecyclerview

import android.view.View
import java.util.*

/**
 * @author Quiet Zhu
 * @date 2020/5/21
 * @description
 */
class MyRecycler {
    private var views: Array<Stack<View>> = arrayOf()
    
    constructor(typeNumber: Int) {
        for (i in 0..typeNumber) {
            views[i] = Stack()
        }
    }
    
    fun put(viewType: Int, view: View) {
        views[viewType].push(view)
    }
    
    fun get(viewType: Int): View? {
        return try {
            views[viewType].pop()
        } catch (e: Exception) {
            null
        }
    }
}