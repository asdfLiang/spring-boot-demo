package com.example.event;

import com.example.entity.SayHelloEntity;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通信检测，是否监听到消息，监听到的消息是否有序
 *
 * @since 2023/3/4 23:43
 * @author by liangzj
 */
@Component
public class CheckSignalEventListener {

    @Async("customExecutor")
    @EventListener
    public void handleSayHello(String message) {
        delay(1000);

        System.out.println("test, the message: \"" + message + "\"");
    }

    @Async("customExecutor")
    @EventListener(condition = "#entity.message != null")
    public void handleSayHelloEntity1(SayHelloEntity entity) {
        delay(1000);

        System.out.println("I am one, I got the entity message: \"" + entity.getMessage() + "\"");
    }

    @Async("customExecutor")
    @EventListener(condition = "#entity != null")
    public void handleSayHelloEntity2(SayHelloEntity entity) {
        delay(100);

        System.out.println("I am two, the entity message is: \"" + entity.getMessage() + "\"");
    }

    /**
     * 模拟延迟
     *
     * @param range
     */
    private void delay(int range) {
        Integer timeout = (int) (Math.random() * range + 1);
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            // nothing to do
        }
    }
}
