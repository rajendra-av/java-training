package com.java.traning.oops.polymorphism;

// Method overloading: several methods share the same name in one class,
// but each has a different parameter list (number, type, or order of parameters).
// The compiler picks the right version at compile time based on the arguments passed.
// The return type alone cannot distinguish overloads.
public class MethodOverloading_PriceCalculator {

    // Overload 1: base price only
    public double calculate(double basePrice) {
        return basePrice;
    }

    // Overload 2: different number of parameters (adds a discount percentage)
    public double calculate(double basePrice, double discountPercent) {
        return basePrice - (basePrice * discountPercent / 100);
    }

    // Overload 3: different number and types of parameters (adds a tax rate)
    public double calculate(double basePrice, double discountPercent, double taxRate) {
        double discounted = calculate(basePrice, discountPercent);   // reuses overload 2
        return discounted + (discounted * taxRate / 100);
    }

    // Overload 4: same number of parameters as overload 2, but different types.
    // A coupon code replaces the numeric discount.
    public double calculate(double basePrice, String couponCode) {
        if ("SAVE10".equalsIgnoreCase(couponCode)) {
            return calculate(basePrice, 10);
        }
        return basePrice;
    }

    // Overload 5: parameters in a different order would also be valid,
    // but mixing orders like this can confuse readers, so use it with care.
    public String calculate(String label, double basePrice) {
        return label + ": " + calculate(basePrice);
    }

    public static void main(String[] args) {
        MethodOverloading_PriceCalculator calc = new MethodOverloading_PriceCalculator();

        // The compiler selects the overload based on the arguments
        System.out.println(calc.calculate(1000));                  // 1000.0
        System.out.println(calc.calculate(1000, 10));              // 900.0
        System.out.println(calc.calculate(1000, 10, 18));          // 1062.0
        System.out.println(calc.calculate(1000, "SAVE10"));        // 900.0
        System.out.println(calc.calculate("Laptop", 1000));        // Laptop: 1000.0
    }
}
