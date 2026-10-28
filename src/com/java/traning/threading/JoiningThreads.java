package com.java.traning.threading;

public class JoiningThreads {

    public static void main(String[] args) throws InterruptedException {
        Thread downloader = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("downloader: chunk " + i + " of 3");
                sleep(200);
            }
        }, "downloader");

        downloader.start();

        // join() blocks main until downloader terminates
        downloader.join();
        System.out.println("main: download complete, now processing");

        // Timed join: wait at most 100 ms, then continue regardless
        Thread slow = new Thread(() -> sleep(1_000), "slow");
        slow.start();
        slow.join(100);
        System.out.println("main: slow still running? " + slow.isAlive());
        slow.interrupt();   // stop it so the program ends promptly
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}