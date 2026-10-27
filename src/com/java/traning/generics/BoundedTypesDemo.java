package com.java.traning.generics;

// Base class with a method every shape must have
class Shape {
    double area() {
        return 0;
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Square extends Shape {
    private final double side;

    Square(double side) {
        this.side = side;
    }

    @Override
    double area() {
        return side * side;
    }
}

public class BoundedTypesDemo {

    // T must be Shape or a subclass of Shape.
    // Because of the bound, we can call area() on any T.
    static <T extends Shape> double totalArea(T first, T second) {
        return first.area() + second.area();
    }

    // Without a bound, this would not compile: Object has no area() method
    // static <T> double totalAreaBroken(T first) {
    //     return first.area();   // compile error
    // }

    public static void main(String[] args) {
        System.out.println("Two circles : " + totalArea(new Circle(1), new Circle(2)));
        System.out.println("Two squares : " + totalArea(new Square(3), new Square(4)));
    }
}