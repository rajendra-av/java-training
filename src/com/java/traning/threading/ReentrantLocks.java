package com.java.traning.threading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {

    static class BankAccount {
        private final ReentrantLock lock = new ReentrantLock();
        private int balance;

        BankAccount(int balance) {
            this.balance = balance;
        }

        void deposit(int amount) {
            lock.lock();                  // acquire the lock
            try {
                balance += amount;
            } finally {
                lock.unlock();            // always release in finally, even if an exception occurs
            }
        }

        // 1. Reentrancy: transfer calls withdraw while already holding the same lock
        void withdraw(int amount) {
            lock.lock();                  // the same thread can acquire it again
            try {
                balance -= amount;
            } finally {
                lock.unlock();
            }
        }

        void transferOut(int amount) {
            lock.lock();                  // first acquisition
            try {
                System.out.println("transferOut holds lock, hold count = " + lock.getHoldCount());
                withdraw(amount);         // second acquisition by the same thread: allowed
            } finally {
                lock.unlock();
            }
        }

        // 2. tryLock: give up instead of waiting forever
        boolean tryDeposit(int amount) throws InterruptedException {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {   // wait at most 100 ms
                try {
                    balance += amount;
                    return true;
                } finally {
                    lock.unlock();
                }
            }
            return false;                 // could not get the lock in time
        }

        int getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        // Reentrancy demo
        account.transferOut(200);
        System.out.println("Balance after transfer: " + account.getBalance());   // 800

        // Multi-threaded deposits: the lock keeps the updates correct
        Thread t1 = new Thread(() -> { for (int i = 0; i < 10_000; i++) account.deposit(1); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 10_000; i++) account.deposit(1); });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Balance after 20,000 deposits: " + account.getBalance());   // 20800

        // tryLock demo: hold the lock in another thread, so tryDeposit fails
        ReentrantLock held = new ReentrantLock();
        Thread holder = new Thread(() -> {
            held.lock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                held.unlock();
            }
        });
        holder.start();
        Thread.sleep(50);
        System.out.println("held.isLocked() = " + held.isLocked());
        if (!held.tryLock()) {
            System.out.println("tryLock() failed immediately: lock is held elsewhere");
        }
        holder.join();

        System.out.println("tryDeposit succeeded: " + account.tryDeposit(50));
    }
}