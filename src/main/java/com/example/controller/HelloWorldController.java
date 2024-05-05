package com.example.controller;

import com.example.entity.SayHelloEntity;
import com.example.service.AsyncService;
import com.example.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @since 2023/3/4 23:33
 * @author by liangzj
 */
@Slf4j
@RestController
public class HelloWorldController {
    private static final Integer PUBLISH_TIMES = 10;

    @Autowired private ApplicationEventPublisher applicationEventPublisher;
    @Autowired private AsyncService asyncService;

    @GetMapping("/hello")
    public String sayHello() {
        for (int i = 0; i < PUBLISH_TIMES; i++) {
            applicationEventPublisher.publishEvent("hello, " + i);
            applicationEventPublisher.publishEvent(new SayHelloEntity("check signal, " + i));
        }

        return "hello world!";
    }

    /** 使用 ThreadLocal + TaskDecorator 的方式向子线程传递参数 */
    @GetMapping("/value2childThread")
    public String value2childThread() throws InterruptedException, ExecutionException {
        // 父线程传值
        UserUtils.setUserId("123456");
        // 子线程取值
        CompletableFuture<String> completableFuture = asyncService.executeValueAsync2();
        String s = completableFuture.get();
        return s;
    }
}
