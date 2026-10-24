package com.java.traning.datatypes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

public class ValueTypesDemo {

    public static void main(String[] args) {

        // 1. BigDecimal: exact decimal values for price, tax, and total
        BigDecimal price = new BigDecimal("249.99");
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal tax = price.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = price.add(tax);

        System.out.println("Price          : " + price);
        System.out.println("Tax            : " + tax);
        System.out.println("Total          : " + total);
        System.out.println("Equal values   : "
                + (new BigDecimal("249.990").compareTo(price) == 0));

        // 2. LocalDate: a date with no time zone
        LocalDate orderDate = LocalDate.of(2026, 10, 24);
        LocalDate dueDate = orderDate.plusDays(30);
        System.out.println("\nOrder date     : " + orderDate);
        System.out.println("Due date       : " + dueDate);
        System.out.println("Day of week    : " + orderDate.getDayOfWeek());
        System.out.println("Is weekend     : "
                + (orderDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || orderDate.getDayOfWeek() == DayOfWeek.SUNDAY));

        // 3. Period: a date-based amount
        Period warranty = Period.between(LocalDate.of(2026, 10, 24), LocalDate.of(2027, 3, 1));
        System.out.println("Period         : " + warranty);

        // 4. LocalDateTime formatted for display
        LocalDateTime placedAt = LocalDateTime.of(2026, 10, 24, 10, 30);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);
        System.out.println("\nPlaced at      : " + placedAt.format(fmt));

        // 5. ZonedDateTime and Instant: the same moment in two representations
        ZonedDateTime indiaTime = placedAt.atZone(ZoneId.of("Asia/Kolkata"));
        Instant utc = indiaTime.toInstant();
        System.out.println("India time     : " + indiaTime);
        System.out.println("UTC instant    : " + utc);

        // 6. Duration: a time-based amount
        Duration delivery = Duration.ofMinutes(90);
        System.out.println("\nDelivery time  : " + delivery + " (" + delivery.toMinutes() + " min)");

        // 7. URI: a parsed identifier
        URI invoiceUri = URI.create("https://example.com/invoices?id=42");
        System.out.println("\nInvoice host   : " + invoiceUri.getHost());
        System.out.println("Invoice query  : " + invoiceUri.getQuery());

        // 8. Locale and Currency
        Locale indiaLocale = Locale.forLanguageTag("en-IN");
        System.out.println("\nLocale         : " + indiaLocale);
        System.out.println("Currency code  : "
                + Currency.getInstance(indiaLocale).getCurrencyCode());
    }
}
