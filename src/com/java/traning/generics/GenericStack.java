package com.java.traning.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// Generic stack: T is a placeholder for whatever type the stack holds.
// The same class works for Strings, Integers, or any other type.
class Stack<T> {
    private final List<T> items = new ArrayList<>();

    void push(T item) {
        items.add(item);
    }

    T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return items.remove(items.size() - 1);
    }

    T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return items.get(items.size() - 1);
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    int size() {
        return items.size();
    }
}

public class GenericStack {
    public static void main(String[] args) {

        // 1. A stack of Strings: a browser's back-button history
        Stack<String> history = new Stack<>();
        history.push("home");
        history.push("products");
        history.push("cart");

        System.out.println("Current page : " + history.peek());
        System.out.println("Going back   : " + history.pop());
        System.out.println("Going back   : " + history.pop());
        System.out.println("Pages left   : " + history.size());

        // 2. A stack of Integers: the same class, a different type
        Stack<Integer> numbers = new Stack<>();
        numbers.push(10);
        numbers.push(20);
        numbers.push(30);

        int top = numbers.pop();          // Integer is unboxed to int here
        System.out.println("\nPopped number: " + top);
        System.out.println("New top      : " + numbers.peek());

        // 3. The compiler rejects a wrong type
        // history.push(42);              // compile error: int cannot be a String

        // 4. Popping an empty stack throws an exception
        Stack<String> empty = new Stack<>();
        try {
            empty.pop();
        } catch (NoSuchElementException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}