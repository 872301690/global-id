package com.verbort;


import com.verbort.bean.Id;
import com.verbort.service.IdService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class Test {


    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-db.xml");
        IdService idService = (IdService) context.getBean("propIdService");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10;i++) {
            Run run = new Run();
            run.countDownLatch = countDownLatch;
            run.idService = idService;
            new Thread(run).start();
        }
        countDownLatch.countDown();


    }
    static class Run implements Runnable{
        CountDownLatch countDownLatch;
        private IdService idService;
        @Override
        public void run() {
            try {
                countDownLatch.await();
                System.out.println(idService.genId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
