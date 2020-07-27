package com.zhu.designpatterns.instance;

/**
 * @author Quiet Zhu
 * @date 2020/7/13
 * @description 静态内部类
 */
public class InnerStaticClassInstance {
    private static class SingletonHolder{
        private static final InnerStaticClassInstance instance = new InnerStaticClassInstance();
    }

    public static InnerStaticClassInstance getInstance() {
        return SingletonHolder.instance;
    }
}
