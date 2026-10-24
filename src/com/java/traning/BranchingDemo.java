package com.java.traning;

public class BranchingDemo {

    public static void main(String[] args) {

        // ---------- 1. Ternary operator ----------
        int stock = 7;
        String stockStatus = stock > 0 ? "In stock" : "Out of stock";
        System.out.println("Ternary        : " + stockStatus);

        // ---------- 2. if / else if / else ----------
        int cartTotal = 1200;
        if (cartTotal >= 5000) {
            System.out.println("If-else        : Free express shipping");
        } else if (cartTotal >= 1000) {
            System.out.println("If-else        : Standard shipping");
        } else {
            System.out.println("If-else        : Shipping charges apply");
        }

        // ---------- 3. switch statement (classic, with fall-through protection) ----------
        char categoryCode = 'G';
        switch (categoryCode) {
            case 'G':
                System.out.println("Switch (old)   : Grocery");
                break;             // without break, execution falls into the next case
            case 'E':
                System.out.println("Switch (old)   : Electronics");
                break;
            case 'C':
            case 'F':              // stacked labels share one body
                System.out.println("Switch (old)   : Clothing or Furniture");
                break;
            default:
                System.out.println("Switch (old)   : Unknown category");
        }

        // ---------- 4. switch expression (arrow form, Java 14+) ----------
        int dayNumber = 6;
        String dayType = switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7          -> "Weekend";
            default            -> "Invalid day";
        };
        System.out.println("Switch expr    : " + dayType);

        // Switch expression with a yield block for multi-line logic
        String discountTier = switch (cartTotal / 500) {
            case 0 -> "No discount";
            case 1 -> "5% off";
            default -> {
                int tier = cartTotal / 500;
                yield tier + "0% off";
            }
        };
        System.out.println("Yield block    : " + discountTier);
    }
}
