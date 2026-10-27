package com.java.traning.collections;
import java.util.HashSet;
import java.util.Set;

public class HashSetContainsDuplicate {

    // Returns true if any value appears at least twice in nums.
    static boolean containsDuplicate(int[] nums) {
        // Holds the values seen so far. A set keeps only unique values.
        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            // add() returns false if the value was already present,
            // which means we have seen a duplicate
            if (!seen.add(num)) {
                return true;
            }
        }

        // No value was repeated
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[] {1, 2, 3, 1}));          // true
        System.out.println(containsDuplicate(new int[] {1, 2, 3, 4}));          // false
        System.out.println(containsDuplicate(new int[] {1, 1, 1, 3, 3, 4, 3})); // true
    }
}