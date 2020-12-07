package com.wonders.ghy.juc.分之合并异步回调;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步回调
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*CompletableFuture<Void> completableFuture=CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"没有返回，update this sql");
        });
        completableFuture.get();*/

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"有返回，update this sql");
            //int age = 10/0;
            return 1024;
        });
        System.out.println(integerCompletableFuture.whenComplete((t, u) -> {
            System.out.println("*****t " + t);
            System.out.println("*****u " + u);
        }).exceptionally(f -> {
            System.out.println("exception:" + f.getMessage());
            return 4444;
        }).get());
    }
}
