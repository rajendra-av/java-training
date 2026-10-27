package com.java.traning.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class StackValidParanthesis {

    // Maps each closing bracket to its matching opening bracket
    private static final Map<Character, Character> PAIRS = Map.of(
            ')', '(',
            '}', '{',
            ']', '['
    );

    // Returns true if every bracket is closed in the correct order.
    static boolean isValid(String s) {
        // ArrayDeque used as a stack: push and pop work at the head
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (PAIRS.containsKey(c)) {
                // Closing bracket: it must match the most recent unmatched opening bracket.
                // An empty stack means there is nothing to match, so the string is invalid.
                if (stack.isEmpty() || stack.pop() != PAIRS.get(c)) {
                    return false;
                }
            } else {
                // Opening bracket: remember it until its closing partner appears
                stack.push(c);
            }
        }

        // Any opening brackets left on the stack were never closed
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));       // true
        System.out.println(isValid("()[]{}"));   // true
        System.out.println(isValid("{[]}"));     // true
        System.out.println(isValid("(]"));       // false
        System.out.println(isValid("([)]"));     // false
        System.out.println(isValid("(("));       // false
    }
}