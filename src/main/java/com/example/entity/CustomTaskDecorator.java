package com.example.entity;

import com.example.utils.UserUtils;

import org.springframework.core.task.TaskDecorator;

/** 线程池修饰类 */
public class CustomTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        // 获取主线程中的请求信息（我们的用户信息也放在里面）
        String robotId = UserUtils.getUserId();
        System.out.println(robotId);

        // 这部分是交给子线程执行的
        return () -> {
            try {
                // 将主线程的请求信息，设置到子线程中
                UserUtils.setUserId(robotId);
                // 执行子线程，这一步不要忘了
                runnable.run();
            } finally {
                // 线程结束，清空这些信息，否则可能造成内存泄漏
                UserUtils.clear();
            }
        };
    }
}
