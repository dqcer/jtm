package dqcer.selenium.model;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 13:12
 * @Description: 线程池配置信息
 */

@Data
public class ThreadPoolConfig {

    /**
     * 核心线程数当线程池初次创建时，是没有任何线程的。当有请求发起时，线程会创建核心线程在请求过程中，
     * 无论核心线程是否闲置，线程池都会创建核心线程，直到满足数量位置
     */
    private int corePoolSize = 5;

    /**
     * 最大线程数 = 核心线程数 + 临时线程数
     */
    private int maximumPoolSize = 10;

    /**
     * 临时线程的存活时间
     */
    private long keepAliveTime = 3000;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * 等待队列，用于存放还未处理的请求
     */
    private BlockingQueue blockingQueue =  new ArrayBlockingQueue<Runnable>(10);

    /**
     * 线程名称，方便回溯
     */
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-test-runner-%d").build();;

}
