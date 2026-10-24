package com.java.traning.oops.encapsulaiton;

// Encapsulation: the state object and operations allowed are combined to form a unit
// How the state of the object is stored or modified is limited to the class itself, and not exposed to outside code.
// This allows the class to enforce its own rules and invariants.

public class BankAccount {

    // 1. Private fields: outside code cannot read or change them directly
    private final String accountNumber;   // final: set once, never changed
    private String ownerName;
    private double balance;
    private boolean frozen;

    // 2. Constructor. Validate input before the object is created
    public BankAccount(String accountNumber, String ownerName, double openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.frozen = false;
    }

    // 3. Getter: read-only access to outside code.
    public String getAccountNumber() {
        return accountNumber;
    }

    // 4. Getter: read-only access to outside code.
    public String getOwnerName() {
        return ownerName;
    }

    // 5. Changes made to the fields are controlled via public setter methods
    // Outside code cannot modify fields directly.
    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        this.ownerName = ownerName.trim();
    }

    public double getBalance() {
        return balance;
    }

    // 7. Behavior methods enforce business rules internally
    public void deposit(double amount) {
        ensureNotFrozen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        ensureNotFrozen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
    }

    public void freeze() {
        this.frozen = true;
    }

    // 8. Private helper: an implementation detail, hidden from callers
    private void ensureNotFrozen() {
        if (frozen) {
            throw new IllegalStateException("Account is frozen");
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("IN-1001", "Asha", 5000.0);

        // Callers use methods, not fields. account.balance = 1000000; would not compile.
        account.deposit(1500.0);
        account.withdraw(500.0);
        System.out.println(account);

        // Validation is enforced by the class itself
        try {
            account.withdraw(999999.0);
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        account.freeze();
        try {
            account.deposit(100.0);
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
