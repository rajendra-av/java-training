package com.java.traning.threading;

public class RaceConditionSynchronizedDemo {

    // Unsafe counter: count++ is three steps (read, add, write), so threads can overwrite each other
    static class UnsafeCounter {
        private int count = 0;

        void increment() {
            count++;
        }

        int get() {
            return count;
        }
    }

    // Safe counter: synchronized methods allow only one thread at a time to enter
    static class SafeCounter {
        private int count = 0;

        synchronized void increment() {   // the lock is the SafeCounter object (this)
            count++;
        }

        synchronized int get() {
            return count;
        }
    }

    // Synchronized block: locks only the code that touches the shared state
    static class BlockCounter {
        private final Object lock = new Object();
        private int count = 0;

        void increment() {
            synchronized (lock) {
                count++;
            }
        }

        int get() {
            synchronized (lock) {
                return count;
            }
        }
    }

    // Runs the given increment action from two threads, 100,000 times each
    static int runWithTwoThreads(Runnable incrementTask, java.util.function.IntSupplier reader)
            throws InterruptedException {
        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(incrementTask);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        return reader.getAsInt();
    }

    public static void main(String[] args) throws InterruptedException {
        final int ITERATIONS = 100_000;

        // 1. Race condition: no synchronization
        UnsafeCounter unsafe = new UnsafeCounter();
        int unsafeResult = runWithTwoThreads(() -> {
            for (int i = 0; i < ITERATIONS; i++) unsafe.increment();
        }, unsafe::get);
        System.out.println("Unsafe counter : expected 200000, got " + unsafeResult);

        // 2. Fixed with synchronized methods
        SafeCounter safe = new SafeCounter();
        int safeResult = runWithTwoThreads(() -> {
            for (int i = 0; i < ITERATIONS; i++) safe.increment();
        }, safe::get);
        System.out.println("Synchronized   : expected 200000, got " + safeResult);

        // 3. Fixed with a synchronized block
        BlockCounter block = new BlockCounter();
        int blockResult = runWithTwoThreads(() -> {
            for (int i = 0; i < ITERATIONS; i++) block.increment();
        }, block::get);
        System.out.println("Sync block     : expected 200000, got " + blockResult);
    }
}