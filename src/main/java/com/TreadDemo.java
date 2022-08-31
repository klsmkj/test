package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreadDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new MyRunable());
        pool.shutdown();
    }

    static class MyRunable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("当前线程"+Thread.currentThread().getName()+":"+i);
            }
        }
    }
}