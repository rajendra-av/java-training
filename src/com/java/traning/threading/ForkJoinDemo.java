package com.java.traning.threading;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    static final int THRESHOLD = 1_000;   // below this size, work directly instead of splitting

    // RecursiveTask: divides the range, computes each half in parallel, and returns the sum
    static class SumTask extends RecursiveTask<Long> {
        private final long[] amounts;
        private final int from, to;

        SumTask(long[] amounts, int from, int to) {
            this.amounts = amounts;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            // Small enough: do the work directly
            if (to - from <= THRESHOLD) {
                long sum = 0;
                for (int i = from; i < to; i++) {
                    sum += amounts[i];
                }
                return sum;
            }

            // Too big: split into two halves
            int mid = (from + to) / 2;
            SumTask left = new SumTask(amounts, from, mid);
            SumTask right = new SumTask(amounts, mid, to);

            left.fork();                        // schedule the left half asynchronously
            long rightResult = right.compute(); // compute the right half in this thread
            long leftResult = left.join();      // wait for the left half's result
            return leftResult + rightResult;
        }
    }

    // RecursiveAction: divides the range and modifies the array in place, with no result
    static class PriceIncreaseAction extends RecursiveAction {
        private final long[] prices;
        private final int from, to;

        PriceIncreaseAction(long[] prices, int from, int to) {
            this.prices = prices;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            if (to - from <= THRESHOLD) {
                for (int i = from; i < to; i++) {
                    prices[i] = prices[i] * 110 / 100;   // 10% increase, integer arithmetic
                }
                return;
            }

            int mid = (from + to) / 2;
            // invokeAll forks both halves and waits for them to finish
            invokeAll(new PriceIncreaseAction(prices, from, mid),
                    new PriceIncreaseAction(prices, mid, to));
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();   // defaults to one worker per CPU core

        // 1. RecursiveTask: sum 1,000,000 order amounts
        long[] amounts = new long[1_000_000];
        Arrays.fill(amounts, 100);
        long total = pool.invoke(new SumTask(amounts, 0, amounts.length));
        System.out.println("Total of all orders : " + total);      // 100000000

        // 2. RecursiveAction: raise all prices by 10%, in parallel
        long[] prices = new long[1_000_000];
        Arrays.fill(prices, 1000);
        pool.invoke(new PriceIncreaseAction(prices, 0, prices.length));
        System.out.println("First price after   : " + prices[0]);       // 1100
        System.out.println("Last price after    : " + prices[prices.length - 1]);  // 1100

        pool.shutdown();
    }
}
