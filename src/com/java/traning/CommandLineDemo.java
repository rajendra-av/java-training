package com.java.traning;

public class CommandLineDemo {

    public static void main(String[] args) {

        // 1. args holds everything typed after the class name, as Strings.
        //    args.length is the number of arguments; args[0] is the first one.
        System.out.println("Number of arguments: " + args.length);

        // 2. Guard against missing arguments before using them
        if (args.length < 2) {
            System.out.println("Usage: java CommandLineDemo <customer> <quantity> [--discount=N]");
            return;
        }

        // 3. Positional arguments: the meaning depends on the position
        String customer = args[0];
        int quantity;
        try {
            quantity = Integer.parseInt(args[1]);   // every argument arrives as a String
        } catch (NumberFormatException e) {
            System.out.println("Quantity must be a whole number, got: " + args[1]);
            return;
        }

        // 4. Optional arguments: look for a flag among the remaining arguments
        int discountPercent = 0;
        for (int i = 2; i < args.length; i++) {
            if (args[i].startsWith("--discount=")) {
                String value = args[i].substring("--discount=".length());
                discountPercent = Integer.parseInt(value);
            } else {
                System.out.println("Ignoring unknown argument: " + args[i]);
            }
        }

        // 5. Use the parsed values
        int unitPrice = 500;
        int subtotal = quantity * unitPrice;
        int total = subtotal - subtotal * discountPercent / 100;

        System.out.println("Customer : " + customer);
        System.out.println("Quantity : " + quantity);
        System.out.println("Discount : " + discountPercent + "%");
        System.out.println("Total    : Rs. " + total);
    }
}