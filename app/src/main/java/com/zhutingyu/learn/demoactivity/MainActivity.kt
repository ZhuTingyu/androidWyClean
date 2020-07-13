package com.zhutingyu.learn.demoactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhutingyu.learn.R


class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg_map)
        
        //        ivHead.setOnClickListener {
        //            barIsVisibility = !barIsVisibility
        //            BarUtils.setStatusBarVisibility(this, barIsVisibility)
        //        }
        
        //手写Android动画框架
        //        ObjectAnimator.ofFloat(View(this), "scaleX", 0f, 1f).start()
        
        //       平行控件
        //        val container = findViewById<ParallaxContainer>(R.id.parallax_container)
        //
        //        container.setUp(
        //            R.layout.view_intro_1,
        //            R.layout.view_intro_2,
        //            R.layout.view_intro_3,
        //            R.layout.view_intro_4,
        //            R.layout.view_intro_5,
        //            R.layout.view_login
        //        )
        //        //设置动画
        //        val ivMan = findViewById<ImageView>(R.id.iv_man)
        //        iv_man.setBackgroundResource(R.drawable.man_run)
        //        container.setIv_man(ivMan)
        //Coordinatorlayout应用
        //        setCoordinatorlayoutDemo()
    }
    
    //layoutId = activity_coordinatorlayout_demo
//    fun setCoordinatorlayoutDemo() {
//        BarUtils.setNavBarImmersive(this)
//        val view = View(this)
//        val barL = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            BarUtils.getStatusBarHeight(this)
//        )
//        view.layoutParams = barL
//        view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//        view.setPadding(0, BarUtils.getStatusBarHeight(this), 0, 0)
//
//
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        val mFeedAdapter = FeedAdapter()
//        recyclerView.adapter = mFeedAdapter
//        recyclerView.setHasFixedSize(true)
//
//        var mSuspensionHeight = 0
//
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                mSuspensionHeight = suspension_bar.height
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val nextViewPostion = layoutManager.findFirstVisibleItemPosition() + 1
//                val nextView = layoutManager.findViewByPosition(nextViewPostion)
//                if (nextView != null) {
//                    if (nextView.y < mSuspensionHeight) {
//                        suspension_bar.y = nextView.y - mSuspensionHeight
//                    } else {
//                        suspension_bar.y = 0f
//                    }
//                }
//                Log.e("1", (layoutManager.findFirstVisibleItemPosition()).toString())
//                tvNickname.setText("NetEase ${layoutManager.findFirstVisibleItemPosition()}")
//
//            }
//        })
//    }
    
}
