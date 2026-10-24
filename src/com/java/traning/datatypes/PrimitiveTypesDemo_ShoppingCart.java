package com.java.traning.datatypes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrimitiveTypesDemo_ShoppingCart {
    // byte: 1 byte
    private byte shelfSlot = 12;

    // short: 2 bytes
    private short storeId = 1024;

    // int: 4 bytes
    private int quantity = 3;

    // long: 8 bytes
    private long orderId = 9_876_543_210L;
    private long placedAtMillis = System.currentTimeMillis();

    // float: 4 bytes
    private float scaleWeightKg = 1.25f;

    // double: 8 bytes
    private double discountRate = 0.075;

    // char: 2 bytes
    private char categoryCode = 'G';

    // boolean: JVM-dependent, typically 1 byte
    private boolean isMember = true;

    // Reference type: exact decimal money values
    private BigDecimal unitPrice = new BigDecimal("249.99");

    public static void main(String[] args) {
        PrimitiveTypesDemo_ShoppingCart cart = new PrimitiveTypesDemo_ShoppingCart();
        cart.printReceipt();
    }

    private void printReceipt() {
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

        BigDecimal discount = isMember
                ? subtotal.multiply(BigDecimal.valueOf(discountRate))
                : BigDecimal.ZERO;

        BigDecimal discountRounded = discount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.subtract(discount).setScale(2, RoundingMode.HALF_UP);

        System.out.println("=== Receipt ===");
        System.out.println("Store ID        : " + storeId);
        System.out.println("Shelf slot      : " + shelfSlot);
        System.out.println("Order ID        : " + orderId);
        System.out.println("Placed at (ms)  : " + placedAtMillis);
        System.out.println("Category        : " + categoryCode);
        System.out.println("Quantity        : " + quantity);
        System.out.println("Item weight (kg): " + scaleWeightKg);
        System.out.println("Unit price      : " + unitPrice);
        System.out.println("Subtotal        : " + subtotal);
        System.out.println("Member          : " + isMember);
        System.out.println("Discount        : " + discountRounded);
        System.out.println("Total           : " + total);
    }

}
