package com.java.traning.collections;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // Returns the indices of the two numbers that add up to target.
    static int[] twoSum(int[] nums, int target) {
        // Maps a value to the index where it was seen
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            // The number needed to reach the target, given the current number
            int complement = target - nums[i];

            // If the complement was already seen, we found the pair
            if (seen.containsKey(complement)) {
                return new int[] { seen.get(complement), i };
            }

            // Otherwise, remember the current number and its index for later
            seen.put(nums[i], i);
        }

        // The problem guarantees a solution, so this line is never reached in valid input
        throw new IllegalArgumentException("No two numbers add up to the target");
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int[] result = twoSum(nums, 9);
        System.out.println("Indices: [" + result[0] + ", " + result[1] + "]");   // [0, 1]

        int[] other = {3, 2, 4};
        int[] result2 = twoSum(other, 6);
        System.out.println("Indices: [" + result2[0] + ", " + result2[1] + "]"); // [1, 2]
    }
}