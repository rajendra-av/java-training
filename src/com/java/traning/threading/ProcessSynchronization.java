package com.java.traning.threading;

public class ProcessSynchronization {

    // Shared slot between cook and waiter. Only one dish fits at a time.
    static class OrderCounter {
        private String dish = null;        // null means the counter is empty

        // Called by the cook (producer)
        public synchronized void place(String newDish) throws InterruptedException {
            // Wait while the counter is full: the waiter hasn't taken the last dish yet.
            // "while", not "if": after waking up, the condition must be checked again.
            while (dish != null) {
                wait();                    // releases the lock and sleeps until notified
            }
            dish = newDish;
            System.out.println("Cook placed    : " + dish);
            notify();                      // wake the waiter, who may be sleeping
        }

        // Called by the waiter (consumer)
        public synchronized String take() throws InterruptedException {
            // Wait while the counter is empty: the cook hasn't placed a dish yet.
            while (dish == null) {
                wait();
            }
            String served = dish;
            dish = null;
            System.out.println("Waiter served  : " + served);
            notify();                      // wake the cook, who may be waiting for an empty slot
            return served;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OrderCounter counter = new OrderCounter();

        Thread cook = new Thread(() -> {
            try {
                for (String dish : new String[] {"Dosa", "Biryani", "Idli"}) {
                    counter.place(dish);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "cook");

        Thread waiter = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    counter.take();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "waiter");

        cook.start();
        waiter.start();
        cook.join();
        waiter.join();

        System.out.println("All orders served.");
    }
}