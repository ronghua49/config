package com.shuyue.inventory_server;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haohua.rong
 * @description
 * @contact 13512835407@163.com
 * @date 2022/5/8 16:52
 */
public class ThreadTest {
    /**
     * 数据不安全的并发测试,HashMap,ConcurrentHashMap 抢购模拟测试。
     */
    @Test
    public void testThread()  {
        Map<String, Goods> map = new ConcurrentHashMap<>();
        Goods apple = new Goods("苹果", 10, 8000.00);
        map.put("apple", apple);

        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    Goods apple2 = map.get("apple");
                    int stock = apple2.getStock();
                    if (stock > 0) {
                        apple2.setStock(stock - 1);
                        System.out.println(Thread.currentThread().getName() + "购买成功");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "购买失败，库存不足");
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }
}
