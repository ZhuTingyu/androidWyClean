package com.zhu.designpatterns.instance;

/**
 * @author Quiet Zhu
 * @date 2020/7/13
 * @description 双重检测
 */
public class DoubleCheckInstance {
    private static DoubleCheckInstance instance;

    public static DoubleCheckInstance getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckInstance.class){
                instance = new DoubleCheckInstance();
            }
        }
        return instance;
    }
}
