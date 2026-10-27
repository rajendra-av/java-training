package com.java.traning.generics;

import java.util.ArrayList;
import java.util.List;

/*class Shape {
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
} */

public class WildCardDemo {

    // 1. Unbounded wildcard: List<?> accepts a list of any element type.
    //    We don't know the element type, so we can only read it as Object,
    //    and we can't add anything except null.
    static void printSize(List<?> items) {
        System.out.println("Items: " + items.size());
        for (Object item : items) {
            System.out.println("  " + item);
        }
        // items.add("x");   // compile error: the element type is unknown
    }

    // 2. Upper-bounded wildcard (producer): List<? extends Shape> holds Shape
    //    or any subclass. The method reads elements out of the list, so
    //    "extends" is used. Each element is safely a Shape.
    static double totalArea(List<? extends Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {          // reading is safe
            total += s.area();
        }
        // shapes.add(new Circle(1));     // compile error: the exact element type is unknown
        return total;
    }

    // 3. Lower-bounded wildcard (consumer): List<? super Circle> holds Circle
    //    or any supertype of Circle (Shape, Object). The method writes
    //    Circles into the list, so "super" is used. Every supertype slot
    //    can safely accept a Circle.
    static void addCircles(List<? super Circle> target, int count) {
        for (int i = 1; i <= count; i++) {
            target.add(new Circle(i));    // writing is safe
        }
        // Circle c = target.get(0);      // compile error: may return only Object
    }

    public static void main(String[] args) {

        // Unbounded: works for any list type
        printSize(List.of("a", "b"));
        printSize(List.of(new Circle(1), new Square(2)));

        System.out.println();

        // Upper-bounded: a producer of Shapes. Both list types are accepted.
        List<Circle> circles = List.of(new Circle(1), new Circle(2));
        List<Square> squares = List.of(new Square(3));
        System.out.println("Circles total: " + totalArea(circles));
        System.out.println("Squares total: " + totalArea(squares));

        System.out.println();

        // Lower-bounded: a consumer of Circles. Targets may be List<Circle>,
        // List<Shape>, or List<Object>.
        List<Circle> circleTarget = new ArrayList<>();
        List<Shape> shapeTarget = new ArrayList<>();
        List<Object> objectTarget = new ArrayList<>();

        addCircles(circleTarget, 2);
        addCircles(shapeTarget, 1);
        addCircles(objectTarget, 1);

        System.out.println("Circle target size : " + circleTarget.size());
        System.out.println("Shape target size  : " + shapeTarget.size());
        System.out.println("Object target size : " + objectTarget.size());
    }
}