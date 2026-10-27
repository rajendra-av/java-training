package com.java.traning.collections;

import java.util.Arrays;
import java.util.TreeSet;

public class SortedSetIntersection {

    // Returns the unique values present in both arrays, in ascending order.
    static int[] intersection(int[] nums1, int[] nums2) {
        // Step 1: store the unique values of nums1 in a sorted set
        TreeSet<Integer> first = new TreeSet<>();
        for (int n : nums1) {
            first.add(n);          // duplicates are ignored automatically
        }

        // Step 2: collect values from nums2 that also appear in first.
        // A TreeSet keeps the result unique and sorted as we add.
        TreeSet<Integer> common = new TreeSet<>();
        for (int n : nums2) {
            if (first.contains(n)) {   // O(log n) lookup in the sorted set
                common.add(n);
            }
        }

        // Step 3: copy the sorted values into an int array
        int[] result = new int[common.size()];
        int i = 0;
        for (int value : common) {
            result[i++] = value;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(intersection(new int[] {1, 2, 2, 1}, new int[] {2, 2})));       // [2]
        System.out.println(Arrays.toString(intersection(new int[] {4, 9, 5}, new int[] {9, 4, 9, 8, 4}))); // [4, 9]
    }
}