package com.java.traning.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapSortByFrequency {

    // Returns the characters of s sorted by decreasing frequency.
    static String frequencySort(String s) {
        // Step 1: count how many times each character appears
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.merge(c, 1, Integer::sum);   // add 1, or start at 1 if absent
        }

        // Step 2: copy the distinct characters into a list so we can sort them
        List<Character> chars = new ArrayList<>(counts.keySet());

        // Step 3: sort by count, highest first
        chars.sort((a, b) -> counts.get(b) - counts.get(a));

        // Step 4: write each character as many times as it appeared
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            for (int i = 0; i < counts.get(c); i++) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(frequencySort("tree"));      // eert or eetr
        System.out.println(frequencySort("cccaaa"));    // aaaccc or cccaaa
        System.out.println(frequencySort("Aabb"));      // bbAa or bbaA
    }
}