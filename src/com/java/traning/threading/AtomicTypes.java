package com.java.traning.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTypes {

    static int plainCount = 0;                       // not thread-safe
    static final Object lock = new Object();
    static int syncCount = 0;                        // protected by synchronized
    static final AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final int ITERATIONS = 100_000;

        // 1. Plain int: lost updates, as in the race-condition demo
        Runnable plain = () -> {
            for (int i = 0; i < ITERATIONS; i++) plainCount++;
        };

        // 2. synchronized: correct, but every increment takes a lock
        Runnable sync = () -> {
            for (int i = 0; i < ITERATIONS; i++) {
                synchronized (lock) {
                    syncCount++;
                }
            }
        };

        // 3. AtomicInteger: correct without a lock, using a single atomic instruction
        Runnable atomic = () -> {
            for (int i = 0; i < ITERATIONS; i++) atomicCount.incrementAndGet();
        };

        run(plain);
        run(sync);
        run(atomic);

        System.out.println("plain int       : " + plainCount);      // usually < 200000
        System.out.println("synchronized    : " + syncCount);       // 200000
        System.out.println("AtomicInteger   : " + atomicCount.get()); // 200000

        // 4. compareAndSet: the building block of atomic classes
        AtomicInteger balance = new AtomicInteger(100);
        boolean ok1 = balance.compareAndSet(100, 150);   // expected 100 matches, so it updates
        boolean ok2 = balance.compareAndSet(100, 200);   // expected 100 no longer matches, so it fails
        System.out.println("CAS 100->150: " + ok1 + ", CAS 100->200: " + ok2
                + ", value = " + balance.get());         // true, false, 150

        // 5. Other common operations
        System.out.println("getAndAdd(10) returns " + balance.getAndAdd(10)
                + ", now " + balance.get());              // 150, now 160
        System.out.println("updateAndGet(x*2) = " + balance.updateAndGet(x -> x * 2));  // 320
    }

    // Starts two threads running the task, then waits for both to finish
    static void run(Runnable task) throws InterruptedException {
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}