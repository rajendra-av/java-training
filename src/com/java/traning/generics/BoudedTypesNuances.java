package com.java.traning.generics;

import java.util.List;

// Base class: the upper bound used in several examples below
/*
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
*/

// An interface used as a second bound
interface Labeled {
    String label();
}

class LabeledSquare extends Square implements Labeled {
    LabeledSquare(double side) {
        super(side);
    }

    @Override
    public String label() {
        return "Labeled square";
    }
}

// Generic class with a bounded type parameter
class ShapeBox<T extends Shape> {
    private final T shape;

    ShapeBox(T shape) {
        this.shape = shape;
    }

    // Returns the exact type T, so the caller needs no cast
    T get() {
        return shape;
    }

    // The bound lets us call area() on the stored value
    double area() {
        return shape.area();
    }
}

public class BoudedTypesNuances {

    // 1. Upper bound: T must be Shape or a subclass.
    //    The bound is what allows calling area() on the parameter.
    static <T extends Shape> double area(T shape) {
        return shape.area();
    }

    // 2. Without a bound, T behaves like Object, which has no area() method.
    //    This would not compile:
    // static <T> double areaBroken(T shape) {
    //     return shape.area();
    // }

    // 3. Same T in two parameters: both arguments must infer one type.
    //    The method returns that exact type, so the caller keeps it.
    static <T extends Shape> T larger(T a, T b) {
        return a.area() >= b.area() ? a : b;
    }

    // 4. Different type variables are allowed to take different types.
    static <T extends Shape, U extends Shape> double sumAreas(T a, U b) {
        return a.area() + b.area();
    }

    // 5. Multiple bounds: the class bound must come first, then interfaces joined with &.
    //    Square fails this bound because it does not implement Labeled.
    //    LabeledSquare satisfies both bounds.
    static <T extends Shape & Labeled> String describe(T item) {
        return item.label() + " with area " + item.area();
    }

    // 6. A bounded T inside a collection parameter
    static <T extends Shape> double totalArea(List<T> shapes) {
        double total = 0;
        for (T s : shapes) {
            total += s.area();
        }
        return total;
    }

    // 7. The wildcard alternative: read-only, no type variable needed
    static double totalAreaWildcard(List<? extends Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {
            total += s.area();
        }
        return total;
    }

    public static void main(String[] args) {

        System.out.println("1. Upper bound, area(Circle(1)): " + area(new Circle(1)));

        System.out.println("\n2. Same T, both Circles: "
                + larger(new Circle(1), new Circle(2)).area());

        // Mixed types: T is inferred as Shape, the nearest common supertype.
        // The result is only known as Shape, so no Square-specific members are available.
        Shape mixed = larger(new Circle(1), new Square(2));
        System.out.println("3. Same T, mixed types, returned area: " + mixed.area());

        System.out.println("\n4. Different type variables: "
                + sumAreas(new Square(2), new Circle(1)));

        System.out.println("\n5. Multiple bounds: "
                + describe(new LabeledSquare(3)));
        // describe(new Square(3));   // compile error: Square is not Labeled

        System.out.println("\n6. Bounded T in a List: "
                + totalArea(List.of(new Circle(1), new Circle(2))));

        System.out.println("7. Wildcard version: "
                + totalAreaWildcard(List.of(new Square(1), new Square(2))));

        // 8. Bounded generic class
        ShapeBox<Circle> box = new ShapeBox<>(new Circle(1));
        Circle inside = box.get();                 // no cast needed
        System.out.println("\n8. ShapeBox<Circle> area: " + inside.area());
        // ShapeBox<String> bad = new ShapeBox<>("x");   // compile error: String is not a Shape
    }
}