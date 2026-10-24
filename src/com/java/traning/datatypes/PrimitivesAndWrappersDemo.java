package com.java.traning.datatypes;

import java.util.ArrayList;
import java.util.List;

public class PrimitivesAndWrappersDemo {

    public static void main(String[] args) {
        // 1. Primitive declarations
        int primitiveInt = 42;
        double primitiveDouble = 3.14;
        boolean primitiveBoolean = true;
        char primitiveChar = 'A';

        // 2. Wrapper class objects (explicit boxing with valueOf)
        Integer boxedInt = Integer.valueOf(primitiveInt);
        Double boxedDouble = Double.valueOf(primitiveDouble);
        Boolean boxedBoolean = Boolean.valueOf(primitiveBoolean);
        Character boxedChar = Character.valueOf(primitiveChar);

        System.out.println("Explicit boxing:");
        System.out.println("  Integer  : " + boxedInt);
        System.out.println("  Double   : " + boxedDouble);
        System.out.println("  Boolean  : " + boxedBoolean);
        System.out.println("  Character: " + boxedChar);

        // 3. Autoboxing: compiler converts primitive to wrapper automatically
        Integer autoBoxedInt = 100;        // int -> Integer
        Double autoBoxedDouble = 2.5;      // double -> Double
        System.out.println("\nAutoboxing:");
        System.out.println("  Integer  : " + autoBoxedInt);
        System.out.println("  Double   : " + autoBoxedDouble);

        // 4. Unboxing: compiler converts wrapper to primitive automatically
        int unboxedInt = autoBoxedInt;     // Integer -> int
        double unboxedDouble = boxedDouble; // Double -> double
        System.out.println("\nUnboxing:");
        System.out.println("  int    : " + unboxedInt);
        System.out.println("  double : " + unboxedDouble);

        // 5. Autoboxing in collections: List<Integer> cannot hold int directly
        List<Integer> scores = new ArrayList<>();
        scores.add(90);                    // autoboxed to Integer
        scores.add(75);
        int total = 0;
        for (int score : scores) {         // unboxed on each iteration
            total += score;
        }
        System.out.println("\nCollection total: " + total);

        // 6. Wrapper utility methods and constants
        System.out.println("\nWrapper utilities:");
        System.out.println("  Integer.MAX_VALUE       : " + Integer.MAX_VALUE);
        System.out.println("  Integer.parseInt(\"123\") : " + Integer.parseInt("123"));
        System.out.println("  Double.parseDouble(\"2.5\"): " + Double.parseDouble("2.5"));
        System.out.println("  Character.isDigit('7')  : " + Character.isDigit('7'));

        // 7. Null pitfall: unboxing a null wrapper throws NullPointerException
        Integer maybeNull = null;
        System.out.println("\nNull check before unboxing:");
        if (maybeNull != null) {
            int safe = maybeNull;
            System.out.println("  Value: " + safe);
        } else {
            System.out.println("  Skipped unboxing, value is null");
        }

        // 8. Equality pitfall: == on wrapper objects compares references
        Integer a = 127;
        Integer b = 127;
        Integer c = 1000;
        Integer d = 1000;
        System.out.println("\nEquality:");
        System.out.println("  127 == 127   : " + (a == b));   // true, cached
        System.out.println("  1000 == 1000 : " + (c == d));   // false, not cached
        System.out.println("  1000.equals  : " + c.equals(d)); // true, compares values
    }
}
