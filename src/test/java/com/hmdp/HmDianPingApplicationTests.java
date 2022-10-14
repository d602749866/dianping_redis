package com.hmdp;

import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIdWork;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HmDianPingApplicationTests {

    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private RedisIdWork redisIdWork;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testIdWork() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(100);

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWork.nextId("order");
                System.out.println("id = " + id);
            }
            latch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            es.submit(task);
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);

    }




    @Test
    void testSaveShop() throws InterruptedException {


        shopService.saveShop2Redis(1L, 10L);
    }

}
