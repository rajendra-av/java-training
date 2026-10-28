package com.java.traning.threading;

public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        // Daemon thread: background service, does not keep the JVM alive
        Thread heartbeat = new Thread(() -> {
            while (true) {
                System.out.println("heartbeat: alive");
                sleep(100);
            }
        }, "heartbeat");
        heartbeat.setDaemon(true);      // must be called before start()
        heartbeat.start();

        System.out.println("heartbeat is daemon: " + heartbeat.isDaemon());

        // Main does its work for 250 ms, then ends
        Thread.sleep(250);
        System.out.println("main finished; JVM exits even though heartbeat loops forever");
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}