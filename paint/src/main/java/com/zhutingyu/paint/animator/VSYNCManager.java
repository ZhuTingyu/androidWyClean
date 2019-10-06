package com.zhutingyu.paint.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quiet Zhu
 * @date 2019-09-22
 * @description 模拟系统广播
 * 用户刷新屏幕用的广播
 */
public class VSYNCManager {
    private static final VSYNCManager ourInstance = new VSYNCManager();

    public static VSYNCManager getInstance() {
        return ourInstance;
    }

    private VSYNCManager() {
        new Thread(runnable).start();
    }

    public void add(AnimationFrameCallback animationFrameCallback) {
        list.add(animationFrameCallback);
    }

    private List<AnimationFrameCallback> list = new ArrayList<>();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (AnimationFrameCallback animationFrameCallback : list) {
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };

    interface AnimationFrameCallback {
        boolean doAnimationFrame(long currentTime);
    }
}
