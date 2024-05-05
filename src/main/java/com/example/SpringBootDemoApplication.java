package com.example;

import com.example.entity.CustomTaskDecorator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @Bean
    public Executor customExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean(name = "inheritableExecutor")
    public Executor inheritableExecutor() {
        log.info("start asyncServiceExecutor----------------");
        // ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 使用可视化运行状态的线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(10);
        // 配置最大线程数
        executor.setMaxPoolSize(10);
        // 配置队列大小
        executor.setQueueCapacity(100);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("inheritable_");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 增加线程池修饰类
        executor.setTaskDecorator(new CustomTaskDecorator());
        // 增加MDC的线程池修饰类
        // executor.setTaskDecorator(new MDCTaskDecorator());
        // 执行初始化
        executor.initialize();
        log.info("end asyncServiceExecutor------------");
        return executor;
    }
}
