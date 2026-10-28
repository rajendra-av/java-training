package com.java.traning.threading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    static final ExecutorService pool = Executors.newFixedThreadPool(4);

    // Simulates a remote call that takes some time
    static int fetchOrderTotal(String customer) {
        sleep(200);
        return 1200;
    }

    // Simulates a remote call that fails for one customer
    static int fetchLoyaltyPoints(String customer) {
        sleep(150);
        if (customer.equals("unknown")) {
            throw new IllegalArgumentException("No loyalty record for " + customer);
        }
        return 50;
    }

    static CompletableFuture<Integer> applyDiscount(int amount) {
        // Returns a new future, so it can be chained with thenCompose
        return CompletableFuture.supplyAsync(() -> amount - amount / 10, pool);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        // 1. Creating and starting async work: runs on our pool, not the common pool
        CompletableFuture<Integer> orderTotal =
                CompletableFuture.supplyAsync(() -> fetchOrderTotal("asha"), pool);
        CompletableFuture<Integer> points =
                CompletableFuture.supplyAsync(() -> fetchLoyaltyPoints("asha"), pool);

        // 2. Transforming and chaining: combine two results, then chain a dependent async step
        CompletableFuture<Integer> finalAmount = orderTotal
                .thenCombine(points, (total, pts) -> total - pts)     // combine independent results
                .thenCompose(CompletableFutureDemo::applyDiscount);   // chain a step that returns a future

        System.out.println("Final amount for asha: " + finalAmount.join());   // 1080 - 10% = 972

        // 3. Handling errors and fallbacks: the customer has no loyalty record
        CompletableFuture<Integer> safePoints = CompletableFuture
                .supplyAsync(() -> fetchLoyaltyPoints("unknown"), pool)
                .exceptionally(ex -> {
                    System.out.println("Fallback used, reason: " + ex.getCause().getMessage());
                    return 0;                                         // default value
                });

        // 4. Retrieving results
        System.out.println("Points with fallback : " + safePoints.join());        // 0

        CompletableFuture<Integer> failing = CompletableFuture
                .supplyAsync(() -> fetchLoyaltyPoints("unknown"), pool);
        try {
            failing.join();                                           // throws CompletionException
        } catch (java.util.concurrent.CompletionException e) {
            System.out.println("join() threw: " + e.getCause().getClass().getSimpleName());
        }

        System.out.println("getNow before completion: "
                + CompletableFuture.supplyAsync(() -> { sleep(500); return 1; }, pool)
                .getNow(-1));                                 // -1, not finished yet

        pool.shutdown();
    }
}