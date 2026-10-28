package com.java.traning.threading;

public class VolatileNotAtomicDemo {

    private static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter++;               // read, add, write: three steps, not atomic
            }
        };

        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start(); b.start();
        a.join();  b.join();

        System.out.println("Expected 200000, got " + counter);   // usually less
    }
}