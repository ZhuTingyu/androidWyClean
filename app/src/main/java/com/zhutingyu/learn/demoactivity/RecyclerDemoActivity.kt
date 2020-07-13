package com.zhutingyu.learn.demoactivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zhutingyu.learn.R
import com.zhutingyu.learn.uidemo.myrecyclerview.MyRecyclerView
import kotlinx.android.synthetic.main.activity_recycler_demo.*


/**
 * @author Quiet Zhu`
 * @date 2020/5/21
 * @description
 */
class RecyclerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_demo)
        list.setAdapter(object : MyRecyclerView.Adapter {
            override fun onCreateViewHodler(
                position: Int,
                parent: ViewGroup?
            ): View? {
                val view = layoutInflater.inflate(R.layout.item_table, parent, false)
                val textView: TextView = view.findViewById(R.id.text1) as TextView
                textView.text = "网易课堂 $position"
                Log.i(
                    "FragmentActivity",
                    "onCreateViewHodler: " + view.hashCode()
                )
                return view
            }
            
            override fun onBinderViewHodler(
                position: Int,
                convertView: View?,
                parent: ViewGroup?
            ): View? {
                val textView =
                    convertView!!.findViewById(R.id.text1) as TextView
                textView.text = "网易课堂 $position"
                Log.i(
                    "FragmentActivity",
                    "onBinderViewHodler: " + convertView.hashCode()
                )
                return convertView
            }
            
            override fun getItemViewType(row: Int): Int {
                return 0
            }
    
            override val viewTypeCount: Int
                get() = 1
    
    
            override var count: Int
                get() = 30
                set(value) {}
            
            override fun getHeight(index: Int): Int {
                return 100
            }
            
        })
    }
}