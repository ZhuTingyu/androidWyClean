package com.zhu.designpatterns.instance;

/**
 * @author Quiet Zhu
 * @date 2020/7/13
 * @description 懒汉式 不支持高并发
 */
public class LazyInstance {
    private static LazyInstance instance;

    public static synchronized LazyInstance getInstance() {
        if (instance == null) {
            instance = new LazyInstance();
        }
        return instance;
    }
}
