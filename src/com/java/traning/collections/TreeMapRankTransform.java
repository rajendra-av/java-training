package com.java.traning.collections;

import java.util.Arrays;
import java.util.TreeMap;

public class TreeMapRankTransform {

    // Returns a new array where each element is replaced by its rank.
    static int[] arrayRankTransform(int[] arr) {
        // Keys are kept in sorted order. Each value maps to its rank (filled in below).
        TreeMap<Integer, Integer> ranks = new TreeMap<>();

        // Step 1: insert every distinct value. Duplicates collapse into one key.
        for (int v : arr) {
            ranks.put(v, 0);
        }

        // Step 2: walk the keys from smallest to largest, assigning ranks 1, 2, 3, ...
        // Replacing a value for an existing key is not a structural change,
        // so iterating over keySet() while doing this is safe.
        int rank = 1;
        for (int key : ranks.keySet()) {
            ranks.put(key, rank++);
        }

        // Step 3: replace each element with the rank of its value
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = ranks.get(arr[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(arrayRankTransform(new int[] {40, 10, 20, 30})));   // [4, 1, 2, 3]
        System.out.println(Arrays.toString(arrayRankTransform(new int[] {100, 100, 100})));    // [1, 1, 1]
        System.out.println(Arrays.toString(
                arrayRankTransform(new int[] {37, 12, 28, 9, 100, 56, 80, 5, 12})));           // [5, 3, 4, 2, 8, 6, 7, 1, 3]
    }
}