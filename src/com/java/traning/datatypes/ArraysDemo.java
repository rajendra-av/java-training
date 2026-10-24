package com.java.traning.datatypes;

import java.util.Arrays;

public class ArraysDemo {

    public static void main(String[] args) {

        // 1. Declaration and creation: size is fixed at creation
        int[] scratch = new int[4];
        System.out.println("Default values : " + Arrays.toString(scratch));  // [0, 0, 0, 0]

        Arrays.fill(scratch, -1);
        System.out.println("After fill     : " + Arrays.toString(scratch));  // [-1, -1, -1, -1]

        // 2. Initialization with literals: parallel arrays describing a cart
        String[] items = {"Keyboard", "Mouse", "Monitor", "Cable"};
        int[] quantities = {2, 1, 1, 3};
        int[] unitPrice = {2500, 500, 13000, 160};   // in rupees

        System.out.println("\nItems length   : " + items.length);
        System.out.println("Third item     : " + items[2]);   // zero-based index

        // 3. Iterating with an indexed for loop, since we need the index
        int grandTotal = 0;
        for (int i = 0; i < items.length; i++) {
            int lineTotal = quantities[i] * unitPrice[i];
            grandTotal += lineTotal;
            System.out.println(items[i] + " x" + quantities[i] + " = " + lineTotal);
        }

        // 4. Enhanced for loop when only the values are needed
        int totalUnits = 0;
        for (int qty : quantities) {
            totalUnits += qty;
        }
        System.out.println("Grand total    : " + grandTotal);
        System.out.println("Total units    : " + totalUnits);

        // 5. Bounds checking: accessing an invalid index throws an exception
        try {
            System.out.println(items[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught         : " + e.getMessage());
        }

        // 6. Sorting: sort a copy, so the original order (linked to quantities and prices) is preserved
        String[] sortedItems = items.clone();
        Arrays.sort(sortedItems);
        System.out.println("\nSorted items   : " + Arrays.toString(sortedItems));
        System.out.println("Original items : " + Arrays.toString(items));

        // 7. Binary search works only on sorted arrays
        int index = Arrays.binarySearch(sortedItems, "Monitor");
        System.out.println("Index of Monitor (sorted): " + index);

        // 8. Reference copy vs. actual copy
        int[] alias = quantities;          // same array, two names
        alias[0] = 5;
        System.out.println("\nAlias changed original : " + quantities[0]);   // 5

        int[] copy = quantities.clone();   // independent copy
        copy[1] = 42;
        System.out.println("Copy [1] : " + copy[1] + ", original [1] : " + quantities[1]);

        // 9. Two-dimensional array: rows are warehouses, columns are products
        int[][] warehouseStock = {
                {10, 4, 0},
                {3, 8, 5}
        };
        System.out.println("\nWarehouse      : " + Arrays.deepToString(warehouseStock));
        System.out.println("Rows x cols    : " + warehouseStock.length + " x " + warehouseStock[0].length);

        // 10. Jagged array: each row can have a different length
        int[][] jagged = new int[2][];
        jagged[0] = new int[3];
        jagged[1] = new int[2];
        System.out.println("Jagged row 1 length : " + jagged[1].length);

        // 11. Equality: == and equals() compare references, Arrays.equals compares contents
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        System.out.println("\na == b         : " + (a == b));           // false
        System.out.println("a.equals(b)    : " + a.equals(b));         // false
        System.out.println("Arrays.equals  : " + Arrays.equals(a, b)); // true
    }
}
