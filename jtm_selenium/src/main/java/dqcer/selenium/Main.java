package dqcer.selenium;

import dqcer.selenium.model.ThreadPoolConfig;
import dqcer.selenium.thread.InitConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 9:52
 * @Description: 程序启动入口
 */
@Slf4j
public class Main {

    public static int number = 5;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = getExecutorService();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(number);
        CountDownLatch downLatch = new CountDownLatch(number);

        for (int i = 0; i < number; i ++){
            executor.submit(new InitConfig(cyclicBarrier, downLatch));
        }
        downLatch.await();
        log.info("当前共运行线程数: {}", InitConfig.atomicInteger);
        executor.shutdown();
    }

    /**
     * 创建线程池
     *
     * @return
     */
    private static ExecutorService getExecutorService() {
        ThreadPoolConfig config = new ThreadPoolConfig();
        return new ThreadPoolExecutor(config.getCorePoolSize(),
                config.getMaximumPoolSize(),
                config.getKeepAliveTime(),
                config.getTimeUnit(),
                config.getBlockingQueue(),
                config.getNamedThreadFactory(),
                (r, executor) ->
                        /*  拒绝策略：指当线程池里面的线程数量达到 maximumPoolSize 且 workQueue 队列已满的情况下被尝试添加进来的任务
                         *  1、ThreadPoolExecutor类提供四种拒绝策略:
                         *      a:new ThreadPoolExecutor.AbortPolicy()  --   对拒绝任务抛弃处理，并且抛出异常
                         *      b:new ThreadPoolExecutor.CallerRunsPolicy() --  这个策略重试添加当前的任务，他会自动重复调用 execute()/submit() 方法，直到成功。
                         *      c:new ThreadPoolExecutor.DiscardOldestPolicy(); --  对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
                         *      d:new ThreadPoolExecutor.DiscardPolicy()    --  对拒绝任务直接无声抛弃，没有异常信息
                         *   2、new RejectedExecutionHandler接口，定义处理，这里采用的是第二种。
                         *      void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
                         */
                        System.err.println("请稍后重试")
        );
    }
}
