package com.java.traning.strings;

public class StringBuilderDemo {

    public static void main(String[] args) {

        // 1. Create a builder, optionally with initial content
        StringBuilder sb = new StringBuilder("Order: ");

        // 2. append() adds to the end and returns the same builder, so calls can be chained
        sb.append("Laptop")
                .append(", qty ")
                .append(2)
                .append(", price ")
                .append(49999.50);

        System.out.println(sb);   // Order: Laptop, qty 2, price 49999.5

        // 3. insert() adds text at a given position
        sb.insert(0, "[NEW] ");
        System.out.println(sb);   // [NEW] Order: Laptop, qty 2, price 49999.5

        // 4. replace() swaps a range of characters
        sb.replace(0, 5, "[PAID]");
        System.out.println(sb);   // [PAID] Order: Laptop, qty 2, price 49999.5

        // 5. delete() removes a range; deleteCharAt() removes one character
        sb.delete(0, 7);          // removes "[PAID] "
        System.out.println(sb);   // Order: Laptop, qty 2, price 49999.5

        // 6. reverse() reverses the characters in place
        StringBuilder word = new StringBuilder("Java");
        System.out.println(word.reverse());   // avaJ

        // 7. length() and charAt() work as they do on String
        System.out.println("Length: " + sb.length());
        System.out.println("First char: " + sb.charAt(0));

        // 8. toString() converts the builder to an immutable String when you're done
        String finalText = sb.toString();
        System.out.println("Final: " + finalText);
    }
}