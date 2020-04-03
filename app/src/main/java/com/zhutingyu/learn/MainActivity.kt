package com.zhutingyu.learn

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhu.utils.BarUtils
import com.zhutingyu.learn.uidemo.FeedAdapter
import kotlinx.android.synthetic.main.activity_coordinatorlayout_demo.*
import kotlinx.android.synthetic.main.activity_expand_text_demo.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_text_demo)
        
        val str =
            "\n崇州电子厂（非捷普）最后5名  5000及以上（下周开始面试入职）每月20日前发工资崇州电子厂（非捷普）最后5名  5000及以上（下周开始面试入职）每月20日前发工资\n1、招聘要求：\n（1）女性， 16-45岁，身体健康、学历不限。\n（2）适应车间环境，适应倒班、能吃苦耐劳、服从管理。\n2、工作内容：电子模具产品手工加工/质量检验等。\n3、工作时间：15天倒班一次，每天10小时左右，月休2-3天。\n4、福利待遇：\n（1）水电费按寝室人头分摊、用餐每天有餐补（正常上班8小时10元，12小时20元每天）。\n（2）入职15元小时+餐费及夜班（12元每晚）补贴+其它补贴等\u003d4800-6000每月，转正调薪（15-16元每小时）、购买五险一金、享受节假日礼品等。\n5、上班地址：崇州工业园区。\n6、      入职电话：,\",\n"
        
        tvContent.setTextString(
            str
        )
        btnOne.setOnClickListener {
            tvContent.setTextString(
                str
            )
        }
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
    fun setCoordinatorlayoutDemo() {
        BarUtils.setNavBarImmersive(this)
        val view = View(this)
        val barL = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            BarUtils.getStatusBarHeight(this)
        )
        view.layoutParams = barL
        view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        view.setPadding(0, BarUtils.getStatusBarHeight(this), 0, 0)
        
        
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val mFeedAdapter = FeedAdapter()
        recyclerView.adapter = mFeedAdapter
        recyclerView.setHasFixedSize(true)
        
        var mSuspensionHeight = 0
        
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mSuspensionHeight = suspension_bar.height
            }
            
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val nextViewPostion = layoutManager.findFirstVisibleItemPosition() + 1
                val nextView = layoutManager.findViewByPosition(nextViewPostion)
                if (nextView != null) {
                    if (nextView.y < mSuspensionHeight) {
                        suspension_bar.y = nextView.y - mSuspensionHeight
                    } else {
                        suspension_bar.y = 0f
                    }
                }
                Log.e("1", (layoutManager.findFirstVisibleItemPosition()).toString())
                tvNickname.setText("NetEase ${layoutManager.findFirstVisibleItemPosition()}")
                
            }
        })
    }
    
}
