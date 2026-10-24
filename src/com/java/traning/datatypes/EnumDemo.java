package com.java.traning.datatypes;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class EnumDemo {

    // 1. Simple enum: a fixed set of named constants
    enum OrderStatus {
        PLACED, PACKED, SHIPPED, DELIVERED, CANCELLED
    }

    // 2. Enum with fields, a constructor, and methods
    enum Size {
        SMALL("S", 0),
        MEDIUM("M", 50),
        LARGE("L", 100),
        XL("XL", 150);

        private final String code;
        private final int extraCharge;

        Size(String code, int extraCharge) {
            this.code = code;
            this.extraCharge = extraCharge;
        }

        public String getCode() {
            return code;
        }

        public int getExtraCharge() {
            return extraCharge;
        }
    }

    // 3. Enum constants with their own behavior (constant-specific class bodies)
    enum Shipping {
        STANDARD {
            @Override
            public int cost(int orderValue) {
                return orderValue >= 1000 ? 0 : 49;   // free above 1000
            }
        },
        EXPRESS {
            @Override
            public int cost(int orderValue) {
                return 149;
            }
        };

        public abstract int cost(int orderValue);
    }

    // 4. Enum implementing an interface
    interface Priced {
        int basePrice();
    }

    enum Plan implements Priced {
        BASIC(199), PRO(499), ENTERPRISE(1999);

        private final int price;

        Plan(int price) {
            this.price = price;
        }

        @Override
        public int basePrice() {
            return price;
        }
    }

    // Exhaustive switch expression: no default needed when every constant is covered
    static String describe(OrderStatus status) {
        return switch (status) {
            case PLACED    -> "Waiting for packing";
            case PACKED    -> "Ready for pickup";
            case SHIPPED   -> "On its way to the customer";
            case DELIVERED -> "Received by the customer";
            case CANCELLED -> "Order cancelled";
        };
    }

    public static void main(String[] args) {

        // ---------- 1. Basic methods every enum has ----------
        OrderStatus status = OrderStatus.SHIPPED;
        System.out.println("name()         : " + status.name());
        System.out.println("ordinal()      : " + status.ordinal());
        System.out.println("toString()     : " + status);

        // valueOf converts a String to a constant
        OrderStatus parsed = OrderStatus.valueOf("DELIVERED");
        System.out.println("valueOf        : " + parsed + " (ordinal " + parsed.ordinal() + ")");

        // An unknown name throws IllegalArgumentException
        try {
            OrderStatus.valueOf("RETURNED");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid name   : caught IllegalArgumentException");
        }

        // ---------- 2. Iterating over all constants ----------
        System.out.println("\nAll statuses:");
        for (OrderStatus s : OrderStatus.values()) {
            System.out.println("  " + s.ordinal() + " " + s + " - " + describe(s));
        }

        // ---------- 3. Comparison: use == and compareTo, which compares ordinals ----------
        System.out.println("\nstatus == SHIPPED       : " + (status == OrderStatus.SHIPPED));
        System.out.println("PLACED.compareTo(SHIPPED): " + OrderStatus.PLACED.compareTo(OrderStatus.SHIPPED));

        // ---------- 4. Enum with fields and methods ----------
        System.out.println("\nSizes:");
        for (Size size : Size.values()) {
            System.out.println("  " + size + " code=" + size.getCode()
                    + " extra=" + size.getExtraCharge());
        }
        Size chosen = Size.valueOf("LARGE");
        int basePrice = 800;
        System.out.println("Chosen " + chosen + " total: " + (basePrice + chosen.getExtraCharge()));

        // ---------- 5. Constant-specific behavior ----------
        System.out.println("\nShipping for 800 : STANDARD=" + Shipping.STANDARD.cost(800)
                + ", EXPRESS=" + Shipping.EXPRESS.cost(800));
        System.out.println("Shipping for 1500: STANDARD=" + Shipping.STANDARD.cost(1500)
                + ", EXPRESS=" + Shipping.EXPRESS.cost(1500));

        // ---------- 6. Enum implementing an interface ----------
        System.out.println("\nPlans:");
        for (Plan plan : Plan.values()) {
            System.out.println("  " + plan + " -> " + plan.basePrice());
        }

        // ---------- 7. EnumMap: fast map keyed by an enum, kept in declaration order ----------
        Map<Size, Integer> stock = new EnumMap<>(Size.class);
        stock.put(Size.XL, 3);
        stock.put(Size.MEDIUM, 12);
        stock.put(Size.SMALL, 20);
        stock.put(Size.LARGE, 7);
        System.out.println("\nEnumMap stock  : " + stock);

        // ---------- 8. EnumSet: compact, fast set of enum constants ----------
        EnumSet<Size> midRange = EnumSet.range(Size.MEDIUM, Size.XL);
        EnumSet<Size> others = EnumSet.complementOf(midRange);
        System.out.println("EnumSet range  : " + midRange);
        System.out.println("Complement     : " + others);
    }
}
