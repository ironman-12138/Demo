package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domin.Goods;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class TestServiceImpl {

    @Resource
    private ThreadPoolServiceImpl threadPoolServiceImpl;
    @Resource
    private Executor executor;

    public void testThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = threadPoolServiceImpl.getExecutorService();
        long startTime = System.currentTimeMillis();    //获取开始时间
        Future<String> f1 = threadPoolServiceImpl.submitTask(executorService, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Thread 1 is running……");
                Thread.sleep(5000); //睡5秒
                return "1 done!";
            }
        });
        Future<String> f2 = threadPoolServiceImpl.submitTask(executorService, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Thread 2 is running……");
                Thread.sleep(5000); //睡5秒
                return "2 done!";
            }
        });

        Future<String> f3 = threadPoolServiceImpl.submitTask(executorService, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Thread 3 is running……");
                Thread.sleep(5000); //睡5秒
                return "3 done!";
            }
        });

        Future<String> f4 = threadPoolServiceImpl.submitTask(executorService, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Thread 4 is running……");
                Thread.sleep(5000); //睡5秒
                return "4 done!";
            }
        });

        Future<String> f5 = threadPoolServiceImpl.submitTask(executorService, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Thread 5 is running……");
                Thread.sleep(5000); //睡5秒
                return "5 done!";
            }
        });

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());
        System.out.println(f5.get());
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }

    public void testThreadPool2() {
        CountDownLatch countDownLatch = new CountDownLatch(3);//倒计时闩锁值目前
        long startTime = System.currentTimeMillis();  //获取开始时间
        executor.execute(() -> {
            try {
                System.out.println("Thread 1 is running……");
                System.out.println("Thread 1的名字：" + executor);
                Thread.sleep(5000); //睡5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info("Thread 1执行完毕，倒计时闩锁现在的值是" + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        });

        executor.execute(() -> {
            try {
                System.out.println("Thread 2 is running……");
                System.out.println("Thread 2的名字：" + executor);
                Thread.sleep(2000); //睡5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info("Thread 2执行完毕，倒计时闩锁现在的值是" + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        });

        executor.execute(() -> {
            try {
                System.out.println("Thread 3 is running……");
                System.out.println("Thread 3的名字：" + executor);
                Thread.sleep(3000); //睡5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info("Thread 3执行完毕，倒计时闩锁现在的值是" + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
            log.info("所有线程执行完毕");
            long endTime = System.currentTimeMillis();    //获取结束时间
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testThreadPool3() {
        CountDownLatch countDownLatch = new CountDownLatch(10);//倒计时闩锁值目前
        long startTime = System.currentTimeMillis();  //获取开始时间
        for (int i = 0; i < 10; i++) {
            String thread = String.valueOf(i);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread " + thread + " ：名称：" + Thread.currentThread().getName() + " is running……");
                        Thread.sleep(2000); //睡2秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        log.info("Thread " + thread + "执行完毕，倒计时闩锁现在的值是" + countDownLatch.getCount());
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await();
            log.info("所有线程执行完毕");
            long endTime = System.currentTimeMillis();    //获取结束时间
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Cacheable(value = {"goodCache"}, key = "#root.methodName", sync = true)
    public List<Goods> getGoodsList() {
        log.info("执行--------------start");
        List<Goods> list = new ArrayList<>();
        list.add(new Goods().setNum(10).setName("华为平板").setId(1001L));
        list.add(new Goods().setNum(20).setName("小米12pro").setId(1002L));
        list.add(new Goods().setNum(50).setName("联想小新16pro").setId(1003L));
        log.info("执行--------------ending");
        return list;
    }

    @CacheEvict(value = "goodCache",key = "'getGoodsList'")
    public void updateGoods() {
        log.info("执行更新-------------->");
    }

}
