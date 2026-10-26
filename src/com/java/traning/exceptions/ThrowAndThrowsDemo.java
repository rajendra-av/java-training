package com.java.traning.exceptions;
import java.io.IOException;

public class ThrowAndThrowsDemo {

    // "throws" declares that this method may throw a checked exception.
    // Callers must catch it or declare it too.
    static void readAccount(String id) throws IOException {
        if (id == null || id.isBlank()) {
            // "throw" creates and raises an exception object at this point.
            throw new IOException("Account ID is missing");
        }
        System.out.println("  Loaded account " + id);
    }

    // "throws" on an unchecked exception is allowed but optional.
    // The compiler does not require callers to handle it.
    static int withdraw(int balance, int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive: " + amount);
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");   // unchecked, not declared
        }
        return balance - amount;
    }

    // Edge case: rethrowing a caught exception, preserving the original as the cause
    static void loadAndWrap(String id) throws Exception {
        try {
            readAccount(id);
        } catch (IOException e) {
            throw new Exception("Could not load account", e);   // chained cause
        }
    }

    // Edge case: throw is a statement, so code after it in the same block is unreachable
    static String unreachableExample(int x) {
        if (x > 0) {
            return "positive";
        }
        throw new IllegalArgumentException("not positive");
        // return "never";  // compile error: unreachable statement
    }

    // Edge case: a method can throw a subclass of a declared exception
    static void checkBranch(String code) throws IOException {
        if (code.equals("X")) {
            throw new java.io.FileNotFoundException("Branch file missing");   // subclass of IOException
        }
    }

    public static void main(String[] args) {

        System.out.println("1. Checked exception with throws:");
        try {
            readAccount("A-100");
            readAccount("");
        } catch (IOException e) {
            System.out.println("  Caught: " + e.getMessage());
        }

        System.out.println("\n2. Unchecked exceptions:");
        try {
            System.out.println("  Remaining: " + withdraw(500, 200));
            withdraw(500, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("  Bad amount: " + e.getMessage());
        }
        try {
            withdraw(500, 900);
        } catch (IllegalStateException e) {
            System.out.println("  Rejected: " + e.getMessage());
        }

        System.out.println("\n3. Rethrow with a cause:");
        try {
            loadAndWrap(null);
        } catch (Exception e) {
            System.out.println("  " + e.getMessage() + ", cause: " + e.getCause().getMessage());
        }

        System.out.println("\n4. Subclass thrown under a declared superclass:");
        try {
            checkBranch("X");
        } catch (IOException e) {
            System.out.println("  Caught as IOException: " + e.getClass().getSimpleName());
        }

        System.out.println("\n5. Throw inside finally-free flow:");
        try {
            unreachableExample(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }
}