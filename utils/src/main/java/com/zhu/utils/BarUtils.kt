package com.zhu.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.Window
import android.view.WindowManager
import androidx.annotation.NonNull


/**
 * @author Quiet Zhu
 * @date 2020-02-18
 * @description 顶部状态栏相关工具
 */
 object BarUtils {
    /**
     * 是否刘海
     * @param context
     * @return
     */
    fun hasNotchInScreen(context: Context): Boolean {
        var ret = false
        try {
            val cl = context.getClassLoader()
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: ClassNotFoundException) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException")
        } catch (e: Exception) {
            Log.e("test", "hasNotchInScreen Exception")
        }
        
        return ret
    }
    
    /**
     * 获取刘海尺寸：width、height,int[0]值为刘海宽度 int[1]值为刘海高度。
     * @param context
     * @return
     */
    fun getNotchSize(context: Context): IntArray {
        var ret = intArrayOf(0, 0)
        try {
            val cl = context.getClassLoader()
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("getNotchSize")
            ret = get.invoke(HwNotchSizeUtil) as IntArray
        } catch (e: ClassNotFoundException) {
            Log.e("test", "getNotchSize ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("test", "getNotchSize NoSuchMethodException")
        } catch (e: Exception) {
            Log.e("test", "getNotchSize Exception")
        }
        
        return ret
    }
    
    /**
     * 设置使用刘海区域
     * @param window
     */
    fun setFullScreenWindowLayoutInDisplayCutout(window: Window?) {
        if (window == null) {
            return
        }
        
        try {
            val layoutParams = window.attributes
            val layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx")
            val con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
            val layoutParamsExObj = con.newInstance(layoutParams)
            val method = layoutParamsExCls.getMethod("addHwFlags", Int::class.javaPrimitiveType)
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT)
        } catch (e: Exception) {
            Log.e("test", "other Exception")
        }
        
    }
    
    /*刘海屏全屏显示FLAG*/
    val FLAG_NOTCH_SUPPORT = 0x00010000
    
    /**
     * 设置应用窗口在华为刘海屏手机不使用刘海
     *
     * @param window 应用页面window对象
     */
    fun setNotFullScreenWindowLayoutInDisplayCutout(window: Window?) {
        if (window == null) {
            return
        }
        try {
            val layoutParams = window.attributes
            val layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx")
            val con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
            val layoutParamsExObj = con.newInstance(layoutParams)
            val method = layoutParamsExCls.getMethod("clearHwFlags", Int::class.javaPrimitiveType)
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT)
        } catch (e: Exception) {
            Log.e("test", "hw clear notch screen flag api error")
        }
        
    }
    
    /*********
     * 1、声明全屏显示。
     *
     * 2、适配沉浸式状态栏，避免状态栏部分显示应用具体内容。
     *
     * 3、如果应用可横排显示，避免应用两侧的重要内容被遮挡。
     */
    
    
    /********************
     * 判断该 OPPO 手机是否为刘海屏手机
     * @param context
     * @return
     */
    fun hasNotchInOppo(context: Context): Boolean {
        return context.getPackageManager()
            .hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }
    
    /**
     * 刘海高度和状态栏的高度是一致的
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) {
            context.getResources().getDimensionPixelSize(resId)
        } else 0
    }
    
    
    /**
     * Vivo判断是否有刘海， Vivo的刘海高度小于等于状态栏高度
     */
    val VIVO_NOTCH = 0x00000020 //是否有刘海
    val VIVO_FILLET = 0x00000008 //是否有圆角
    
    fun hasNotchAtVivo(context: Context): Boolean {
        var ret = false
        try {
            val classLoader = context.getClassLoader()
            val FtFeature = classLoader.loadClass("android.util.FtFeature")
            val method = FtFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            ret = method.invoke(FtFeature, VIVO_NOTCH) as Boolean
        } catch (e: ClassNotFoundException) {
            Log.e("Notch", "hasNotchAtVivo ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException")
        } catch (e: Exception) {
            Log.e("Notch", "hasNotchAtVivo Exception")
        } finally {
            return ret
        }
    }
    
    
    /**
     * 设置沉浸式
     */
    fun setNavBarImmersive(activity: Activity) {
        //设置沉浸式
        val decorView = activity.window.decorView
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val uiOptions = (SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
    }
    
    fun setStatusBarVisibility(activity: Activity, isVisible: Boolean) {
        if (isVisible) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}