package com.zhu.designpatterns.instance;

/**
 * @author Quiet Zhu
 * @date 2020/7/13
 * @description 饿汉式单例 类加载时初始化示例 不支持延迟加载
 */
public class HungryInstance {
    private static final HungryInstance instance = new HungryInstance();

    public static HungryInstance getInstance() {
        return instance;
    }
}
