package com.java.traning.threading;

public class VolatileDemo {

    // Remove "volatile" to see the loop possibly never stop
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long count = 0;
            // Without volatile, the JIT may read "running" once and turn this into
            // while (true), because nothing inside the loop changes it
            while (running) {
                count++;
            }
            System.out.println("worker stopped after " + count + " iterations");
        });

        worker.start();
        Thread.sleep(500);
        running = false;                 // written by main, read by worker
        System.out.println("main requested stop");
        worker.join(2_000);              // wait at most 2 seconds

        if (worker.isAlive()) {
            System.out.println("worker still running: the stop flag was not seen");
            worker.interrupt();          // (shown only for cleanup)
        } else {
            System.out.println("worker terminated normally");
        }


    }
}