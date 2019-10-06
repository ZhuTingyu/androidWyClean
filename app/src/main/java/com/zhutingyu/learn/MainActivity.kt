package com.zhutingyu.learn

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ObjectAnimator.ofFloat(View(this),"scaleX",0f, 1f)
    }
}
