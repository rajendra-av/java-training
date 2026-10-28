package com.java.traning.threading;

public class ThreadLifeCycle {

    public static void main(String[] args) throws InterruptedException {
        // NEW: created but not started
        Thread t = new Thread(() -> sleep(300), "lifecycle-worker");
        System.out.println("After creation : " + t.getState());     // NEW

        // RUNNABLE: start() makes it eligible to run
        t.start();
        System.out.println("After start    : " + t.getState());     // RUNNABLE

        // TIMED_WAITING: inside sleep()
        Thread.sleep(50);
        System.out.println("While sleeping : " + t.getState());     // TIMED_WAITING

        // TERMINATED: run() has returned
        t.join();
        System.out.println("After finish   : " + t.getState());     // TERMINATED

        // BLOCKED: waiting to enter a synchronized block held by another thread
        Object lock = new Object();
        Thread holder = new Thread(() -> {
            synchronized (lock) {
                sleep(300);
            }
        });
        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                System.out.println("waiter acquired the lock");
            }
        });
        holder.start();
        Thread.sleep(50);
        waiter.start();
        Thread.sleep(50);
        System.out.println("Waiter while blocked: " + waiter.getState());   // BLOCKED
        holder.join();
        waiter.join();

        // WAITING: untimed wait() until notified
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        waitingThread.start();
        Thread.sleep(50);
        System.out.println("Waiting thread        : " + waitingThread.getState());   // WAITING
        synchronized (lock) {
            lock.notify();
        }
        waitingThread.join();
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}