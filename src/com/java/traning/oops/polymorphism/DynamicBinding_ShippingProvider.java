package com.java.traning.oops.polymorphism;

// Base class: defines the contract every shipping provider must follow.
// Client code depends only on this type.
abstract class ShippingProvider {

    // Each subclass supplies its own pricing formula
    public abstract double cost(double weightKg);

    // Each subclass supplies its own name and delivery promise
    public abstract String name();
    public abstract String deliveryNote();

    // Shared logic written once in the base class. It calls the abstract methods,
    // and Java runs the subclass's version of each one (dynamic binding).
    public String quote(double weightKg) {
        return String.format("%s: Rs. %.2f, arrives %s",
                name(), cost(weightKg), deliveryNote());
    }
}

class StandardShipping extends ShippingProvider {
    @Override
    public double cost(double weightKg) {
        return 40 + 25 * weightKg;
    }

    @Override
    public String name() {
        return "Standard";
    }

    @Override
    public String deliveryNote() {
        return "within 5 days";
    }
}

class ExpressShipping extends ShippingProvider {
    @Override
    public double cost(double weightKg) {
        return 120 + 40 * weightKg;
    }

    @Override
    public String name() {
        return "Express";
    }

    @Override
    public String deliveryNote() {
        return "within 2 days";
    }
}

class SameDayShipping extends ShippingProvider {
    @Override
    public double cost(double weightKg) {
        return 250 + 60 * weightKg;
    }

    @Override
    public String name() {
        return "Same-day";
    }

    @Override
    public String deliveryNote() {
        return "today by 9 PM";
    }
}

// Client code. It knows only ShippingProvider, never the concrete subclasses.
// Adding a new provider later requires no change to this class.
class OrderService {
    public void dispatch(ShippingProvider provider, double weightKg) {
        // The reference type is ShippingProvider, but the object's real class
        // decides which cost(), name(), and deliveryNote() run.
        System.out.println("Order shipped -> " + provider.quote(weightKg));
    }
}

public class DynamicBinding_ShippingProvider {

    // Chooses the concrete object at runtime, returning the base type
    static ShippingProvider selectProvider(String customerTier) {
        return switch (customerTier) {
            case "URGENT"  -> new SameDayShipping();
            case "PREMIUM" -> new ExpressShipping();
            default        -> new StandardShipping();
        };
    }

    public static void main(String[] args) {
        OrderService service = new OrderService();
        double weightKg = 2.5;

        // The variable type never changes, but the object behind it does
        ShippingProvider provider = selectProvider("PREMIUM");
        service.dispatch(provider, weightKg);

        provider = selectProvider("URGENT");
        service.dispatch(provider, weightKg);

        provider = selectProvider("STANDARD");
        service.dispatch(provider, weightKg);
    }
}