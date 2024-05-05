package com.example.service;

import com.example.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @since 2024/4/30 下午10:45
 * @author by liangzj
 */
@Slf4j
@Service
public class AsyncService {

    /** 使用ThreadLocal方式传递 带有返回值 */
    @Async("inheritableExecutor")
    public CompletableFuture<String> executeValueAsync2() {
        log.info("start executeValueAsync");
        System.out.println("异步线程执行返回结果......+");
        log.info("end executeValueAsync");
        return CompletableFuture.completedFuture(UserUtils.getUserId());
    }
}
