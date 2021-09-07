package com.xy.nebulaol.common.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * description: ThreadPoolConfig
 * date: 2020-09-13 10:30
 * author: chenxd
 * version: 1.0
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Value("${async.executor.thread.corePoolSize}")
    private int corePoolSize;
    @Value("${async.executor.thread.maxPoolSize}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queueCapacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.namePrefix}")
    private String threadNamePrefix;
    @Value("${async.executor.thread.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean("taskExecutor")
    public Executor payAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setTaskDecorator(new MdcTaskDecorator());

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
