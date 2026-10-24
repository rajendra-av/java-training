package com.java.traning;

public class LoopingDemo {

    public static void main(String[] args) {

        // ---------- 1. for loop ----------
        System.out.print("For loop       : ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // ---------- 2. Enhanced for loop (for-each) ----------
        String[] items = {"Milk", "Bread", "Eggs"};
        System.out.println("For-each       :");
        for (String item : items) {
            System.out.println("  - " + item);
        }

        // ---------- 3. while loop ----------
        int remaining = 3;
        System.out.print("While loop     : ");
        while (remaining > 0) {
            System.out.print("Retry" + remaining + " ");
            remaining--;
        }
        System.out.println();

        // ---------- 4. do-while loop (body runs at least once) ----------
        int attempts = 0;
        boolean paymentSucceeded;
        do {
            attempts++;
            paymentSucceeded = attempts == 2;   // simulated: succeeds on 2nd try
            System.out.println("Do-while       : Attempt " + attempts
                    + (paymentSucceeded ? " succeeded" : " failed"));
        } while (!paymentSucceeded && attempts < 3);

        // ---------- 5. break and continue ----------
        System.out.print("Continue/break : ");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;          // skip even numbers
            }
            if (i > 7) {
                break;             // stop after 7
            }
            System.out.print(i + " ");
        }
    }
}

