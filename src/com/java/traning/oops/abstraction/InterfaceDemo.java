package com.java.traning.oops.abstraction;

import java.util.List;

interface PaymentMethod {

    double MAX_SINGLE_TRANSACTION = 100000.0;

    String name();

    // The core capability: attempt to charge this payment method.
    // Returns true if the payment succeeded, false if it was declined.
    boolean pay(double amount);

}

interface Refundable {
    void refund(double amount);
}

class CreditCard implements PaymentMethod, Refundable {
    private final String maskedNumber;

    CreditCard(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    @Override
    public String name() {
        return "Credit card " + maskedNumber;
    }

    // Card-specific rules (here, a limit) live inside the implementation
    @Override
    public boolean pay(double amount) {
        if (amount > 50000) {
            return false;
        }
        // Real code would call the card network here
        return true;
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunded " + amount + " to " + maskedNumber);
    }
}

class UpiPayment implements PaymentMethod {
    private final String upiId;

    UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public String name() {
        return "UPI (" + upiId + ")";
    }

    @Override
    public boolean pay(double amount) {
        // Real code would request approval from the user's bank app
        return true;
    }
}

class Checkout {
    // Checkout applies the business rule for every method, then delegates the payment
    public void pay(PaymentMethod method, double amount) {
        if (amount > PaymentMethod.MAX_SINGLE_TRANSACTION) {
            System.out.println("Amount exceeds single transaction limit");
            return;
        }
        if (!method.pay(amount)) {
            System.out.println("Declined by " + method.name());
            return;
        }

        System.out.println("Payment of " + amount + " accepted via " + method.name());
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Checkout checkout = new Checkout();

        List<PaymentMethod> methods = List.of(
                new CreditCard("**** 4242"),
                new UpiPayment("asha@bank")
        );

        for (PaymentMethod method : methods) {
            checkout.pay(method, 2500.00);
        }
    }
}