package com.zhutingyu.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhu.utils.BarUtils
import kotlinx.android.synthetic.main.activity_layout.*


class MainActivity : AppCompatActivity() {
    var barIsVisibility = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setNavBarImmersive(this)
        setContentView(R.layout.activity_layout)
        ivHead.setOnClickListener {
            barIsVisibility = !barIsVisibility
            BarUtils.setStatusBarVisibility(this, barIsVisibility)
        }
        
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
    }
    
}
