package com.java.traning.exceptions;

public class MultiTryCatch {

    static int divide(String numerator, String denominator) {
        return Integer.parseInt(numerator) / Integer.parseInt(denominator);
    }

    public static void main(String[] args) {

        // Multiple catch blocks: the most specific type comes first
        String[][] inputs = {
                {"10", "2"},     // works
                {"10", "0"},     // ArithmeticException
                {"abc", "2"}     // NumberFormatException
        };

        for (String[] pair : inputs) {
            try {
                System.out.println("Result: " + divide(pair[0], pair[1]));
            } catch (NumberFormatException e) {
                System.out.println("Not a number: " + pair[0] + " or " + pair[1]);
            } catch (ArithmeticException e) {
                System.out.println("Cannot divide by zero");
            } finally {
                // Runs after every attempt, whether it succeeded or an exception was caught
                System.out.println("Finished attempt for " + pair[0] + " / " + pair[1]);
            }
        }

        // Multi-catch (Java 7+): one block for several exception types
        try {
            divide("abc", "0");
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Input or math error: " + e.getClass().getSimpleName());
        }

        System.out.println("Program continues");
    }
}