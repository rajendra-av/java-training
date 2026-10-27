package com.java.traning.collections;

import java.util.ArrayList;
import java.util.List;

public class ArrayListPascalsTriangle {

    // Returns the first numRows rows of Pascal's triangle.
    static List<List<Integer>> generate(int numRows) {
        // The outer list grows one row at a time, so ArrayList fits
        List<List<Integer>> triangle = new ArrayList<>();

        for (int row = 0; row < numRows; row++) {
            // Each row has row + 1 elements
            List<Integer> current = new ArrayList<>();

            for (int col = 0; col <= row; col++) {
                // The first and last element of every row are always 1
                if (col == 0 || col == row) {
                    current.add(1);
                } else {
                    // Interior value: sum of the two values above it in the previous row
                    List<Integer> previous = triangle.get(row - 1);
                    current.add(previous.get(col - 1) + previous.get(col));
                }
            }

            triangle.add(current);   // append the finished row
        }

        return triangle;
    }

    public static void main(String[] args) {
        List<List<Integer>> result = generate(5);

        for (List<Integer> row : result) {
            System.out.println(row);
        }
    }
}