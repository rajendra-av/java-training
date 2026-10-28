package com.java.traning.threading;

public class DeadLockAndPrevention {

    static class Account {
        final int id;
        int balance;

        Account(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }
    }

    // DEADLOCK: nested synchronized blocks taken in caller-dependent order.
    // Thread 1 holds A and waits for B, while thread 2 holds B and waits for A.
    static void unsafeTransfer(Account from, Account to, int amount) throws InterruptedException {
        synchronized (from) {
            Thread.sleep(100);              // widen the window so both threads grab their first lock
            synchronized (to) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }

    // PREVENTION: consistent lock ordering. Always lock the smaller id first.
    // Every thread acquires locks in the same global order, so no cycle can form.
    static void orderedTransfer(Account from, Account to, int amount) {
        Account first = from.id < to.id ? from : to;
        Account second = from.id < to.id ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 1. Deadlock demo
        Account a = new Account(1, 1000);
        Account b = new Account(2, 1000);

        Thread t1 = new Thread(() -> {
            try { unsafeTransfer(a, b, 100); } catch (InterruptedException ignored) { }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try { unsafeTransfer(b, a, 50); } catch (InterruptedException ignored) { }
        }, "t2");
        t1.setDaemon(true);                 // lets the JVM exit although the threads are stuck
        t2.setDaemon(true);
        t1.start();
        t2.start();
        t1.join(1000);
        t2.join(1000);
        if (t1.isAlive() && t2.isAlive()) {
            System.out.println("Deadlock detected: t1 state = " + t1.getState()
                    + ", t2 state = " + t2.getState());
        }

        // 2. Prevention with consistent lock ordering
        Account c = new Account(1, 1000);
        Account d = new Account(2, 1000);
        Thread p1 = new Thread(() -> orderedTransfer(c, d, 100));
        Thread p2 = new Thread(() -> orderedTransfer(d, c, 50));
        p1.start();
        p2.start();
        p1.join();
        p2.join();
        System.out.println("Ordered transfers finished: c=" + c.balance + ", d=" + d.balance);
    }
}