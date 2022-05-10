package org.example.service.impl;

import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ThreadPoolServiceImpl {

    /**
     * 必须要自行处理提交异常，否则线程执行会中断，即使提交成功也无法继续执行
     * @param executorService
     * @param callable
     * @return
     */
    public Future<String> submitTask(ExecutorService executorService, Callable<String> callable){
        try{
            Future<String> f = executorService.submit(callable);
            return f;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ExecutorService getExecutorService(){
        int poolSize = Runtime.getRuntime().availableProcessors();
        //超出队列的size，再继续添加任务，会直接根据RejectedExecutionHandler来决定处理新任务的策略
        //AbortPolicy : 对于超出队列大小后面再添加的任务直接抛异常
        //DiscardPolicy	: 什么也不做，直接忽略
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0, TimeUnit.SECONDS, queue, handler);
        return executorService;
    }

}
