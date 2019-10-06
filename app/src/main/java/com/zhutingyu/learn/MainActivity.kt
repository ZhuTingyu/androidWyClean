package com.zhutingyu.learn

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zhutingyu.paint.animator.MyObjectAnimator
import com.zhutingyu.paint.animator.MyTimeInterpolator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ObjectAnimator.ofFloat(View(this), "scaleX", 0f, 1f).start()
    }

    fun scale(view: View) {
//        val objectAnimator = MyObjectAnimator.ofFloat(button, "scaleX", 2f)
//        objectAnimator.setDuration(3000)
//        objectAnimator.start()
        val animator = ObjectAnimator.ofFloat(button, "scaleX",  2f)
        animator.interpolator = MyTimeInterpolator()
        animator.duration = 3000
        animator.start()
    }
}
