package com.java.traning.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolComparision {
    static final int TASKS = 10_000;

    // A small unit of work: simulates a short I/O call that waits 10 ms
    static void simulatedWork() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 1. One new thread per task
        AtomicInteger peakThreads = new AtomicInteger();
        long start = System.nanoTime();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < TASKS; i++) {
            Thread t = new Thread(ThreadPoolComparision::simulatedWork);
            threads.add(t);
            t.start();
            peakThreads.updateAndGet(p -> Math.max(p, Thread.activeCount()));
        }
        for (Thread t : threads) {
            t.join();
        }
        long threadMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Thread per task    : " + TASKS + " tasks, " + threadMs + " ms, "
                + "peak active threads ~ " + peakThreads.get());

        // 2. ThreadPoolExecutor: 8 reused workers and an unbounded queue for waiting tasks
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                8,                                  // core pool size: workers kept alive
                8,                                  // maximum pool size
                0L, TimeUnit.MILLISECONDS,          // keep-alive (unused, since core == max)
                new LinkedBlockingQueue<>()         // queue for tasks waiting for a free worker
        );

        start = System.nanoTime();
        for (int i = 0; i < TASKS; i++) {
            pool.execute(ThreadPoolComparision::simulatedWork);
        }
        pool.shutdown();                            // stop accepting tasks, finish queued ones
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long poolMs = (System.nanoTime() - start) / 1_000_000;

        // getPoolSize() is 0 here because the workers have exited.
        // getLargestPoolSize() keeps the peak number of workers reached during the run.
        // if the number of threads created is less than pool size,
        // getlargestPoolSize() will give the number of threads created.
        System.out.println("ThreadPoolExecutor : " + TASKS + " tasks, " + poolMs + " ms, "
                + "peak active threads = " + pool.getLargestPoolSize()
                + ", completed = " + pool.getCompletedTaskCount());
    }
}