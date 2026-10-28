package com.java.traning.threading;

public class ThreadInterruption {

    public static void main(String[] args) throws InterruptedException {

        // Case A: thread blocked in sleep() wakes with InterruptedException
        Thread sleeper = new Thread(() -> {
            try {
                Thread.sleep(10_000);
                System.out.println("sleeper: woke up normally");
            } catch (InterruptedException e) {
                System.out.println("sleeper: interrupted while sleeping");
                Thread.currentThread().interrupt();     // restore the flag for callers
            }
            System.out.println("sleeper: isInterrupted() = " + Thread.currentThread().isInterrupted());
        }, "sleeper");
        sleeper.start();
        Thread.sleep(50);
        sleeper.interrupt();
        sleeper.join();

        // Case B: running code checks the flag cooperatively
        Thread worker = new Thread(() -> {
            long count = 0;
            while (!Thread.currentThread().isInterrupted()) {   // no exception here, just a flag
                count++;
            }
            System.out.println("worker: stopped after counting, flag was set");
        }, "worker");
        worker.start();
        Thread.sleep(50);
        worker.interrupt();
        worker.join();

        // Case C: a common mistake: swallowing the exception
        Thread bad = new Thread(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                // Empty catch: the interrupt is lost, and the caller never learns about it
            }
            System.out.println("bad: continuing as if nothing happened, isInterrupted() = "
                    + Thread.currentThread().isInterrupted());
        }, "bad");
        bad.start();
        Thread.sleep(50);
        bad.interrupt();
        bad.join();
    }
}