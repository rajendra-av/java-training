package com.java.traning.exceptions;

public class FinallyEdgeCasesDemo {

    // Edge case 1: return inside catch. finally still runs before the method returns.
    static int returnInCatch() {
        int value = 1;
        try {
            throw new RuntimeException("boom");
        } catch (RuntimeException e) {
            value = 2;
            System.out.println("  catch: value = " + value);
            return value;                 // the value 2 is saved here
        } finally {
            value = 3;                    // changes the local variable only
            System.out.println("  finally: value = " + value);
        }                                 // the method still returns 2
    }

    // Edge case 2: return before the try block. finally is never reached.
    static String returnBeforeTry(boolean earlyExit) {
        if (earlyExit) {
            return "returned early";      // try/finally below is skipped entirely
        }
        try {
            return "returned from try";
        } finally {
            System.out.println("  finally runs in normal path");
        }
    }

    // Edge case 3a: return inside finally overrides the try result and discards the exception.
    @SuppressWarnings("finally")
    static int returnInFinally() {
        try {
            throw new IllegalStateException("lost");
        } finally {
            return 99;  // the exception is silently swallowed
        }
    }

    // Edge case 3b: an exception thrown in finally replaces the original exception.
    static void exceptionInFinally() {
        try {
            throw new RuntimeException("original");
        } finally {
            throw new IllegalArgumentException("from finally");
        }
    }

    // Edge case 3c: a returned object is a reference, so finally can change what the caller sees.
    static StringBuilder mutableReturn() {
        StringBuilder sb = new StringBuilder("start");
        try {
            return sb;
        } finally {
            sb.append(" + finally");
        }
    }

    // Edge case 3d: nested try-finally. The inner finally runs first, then the outer catch and finally.
    static void nested() {
        try {
            try {
                System.out.println("  inner try");
                throw new RuntimeException("inner failure");
            } finally {
                System.out.println("  inner finally");
            }
        } catch (RuntimeException e) {
            System.out.println("  outer catch: " + e.getMessage());
        } finally {
            System.out.println("  outer finally");
        }
    }

    public static void main(String[] args) {

        System.out.println("1. return in catch:");
        int r = returnInCatch();
        System.out.println("  Returned: " + r);

        System.out.println("\n2. return before try:");
        System.out.println("  Early: " + returnBeforeTry(true));
        System.out.println("  Normal: " + returnBeforeTry(false));

        System.out.println("\n3. other edge cases:");
        System.out.println("3a. return in finally swallows exception: " + returnInFinally());

        try {
            exceptionInFinally();
        } catch (RuntimeException e) {
            System.out.println("3b. caught: " + e.getMessage());
        }

        System.out.println("3c. mutable object changed by finally: " + mutableReturn());

        System.out.println("3d. nested:");
        nested();
    }
}