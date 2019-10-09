package com.zhutingyu.learn

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.zhutingyu.paint.pingxinview.ParallaxContainer
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ObjectAnimator.ofFloat(View(this), "scaleX", 0f, 1f).start()

        val container = findViewById<ParallaxContainer>(R.id.parallax_container)

        container.setUp(
            R.layout.view_intro_1,
            R.layout.view_intro_2,
            R.layout.view_intro_3,
            R.layout.view_intro_4,
            R.layout.view_intro_5,
            R.layout.view_login
        )
        //设置动画
        val ivMan = findViewById<ImageView>(R.id.iv_man)
        iv_man.setBackgroundResource(R.drawable.man_run)
        container.setIv_man(ivMan)
    }

}
