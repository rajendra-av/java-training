package com.java.traning.threading;

public class CreatingThreads {

    // 1. Extending Thread: override run(), then call start()
    static class GreeterThread extends Thread {
        private final String name;

        GreeterThread(String name) {
            super("greeter-" + name);     // sets the thread's name
            this.name = name;
        }

        @Override
        public void run() {
            // This code executes on the new thread, not on main
            System.out.println("[" + getName() + "] Hello, " + name);
        }
    }

    public static void main(String[] args) throws Exception {

        // 1. Subclass Thread
        Thread t1 = new GreeterThread("Asha");
        t1.start();                       // start() creates the thread and calls run() on it
        t1.join();                        // wait for t1 to finish before continuing

        // 2. Implement Runnable with a lambda. Preferred: the task is separate from the thread.
        Runnable task = () -> System.out.println("[" + Thread.currentThread().getName()
                + "] Runnable task running");
        Thread t2 = new Thread(task, "runnable-thread");
        t2.start();
        t2.join();

        // 3. Anonymous class implementing Runnable (the pre-lambda style)
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[" + Thread.currentThread().getName()
                        + "] Anonymous Runnable running");
            }
        }, "anonymous-thread");
        t3.start();
        t3.join();

        // 4. Important: start() vs run()
        Thread direct = new Thread(() ->
                System.out.println("[" + Thread.currentThread().getName() + "] started"),
                "worker");
        direct.run();     // runs on the main thread: no new thread is created
        direct.start();   // runs on the new thread "worker"
        direct.join();    // cannot call start() again: it throws IllegalThreadStateException

        System.out.println("[" + Thread.currentThread().getName() + "] main finished");
    }
}