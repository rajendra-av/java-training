package com.java.traning.exceptions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Checked custom exception: a business rule the caller is expected to handle.
// It carries the details needed to show a message or offer an alternative.
class InsufficientFundsException extends Exception {
    private final String accountId;
    private final BigDecimal available;
    private final BigDecimal requested;

    public InsufficientFundsException(String accountId, BigDecimal available, BigDecimal requested) {
        super(String.format("Account %s has %s available but %s was requested",
                accountId, available, requested));
        this.accountId = accountId;
        this.available = available;
        this.requested = requested;
    }

    public BigDecimal getShortfall() {
        return requested.subtract(available);
    }

    public String getAccountId() {
        return accountId;
    }
}

// Unchecked custom exception: an invalid reference is a programming or integration error,
// so callers aren't forced to catch it everywhere.
class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountId) {
        super("No account with id " + accountId);
    }
}

class Account {
    final String id;
    BigDecimal balance;

    Account(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
}

class TransferService {
    private final Map<String, Account> accounts = new HashMap<>();

    void open(Account account) {
        accounts.put(account.id, account);
    }

    // Declares the checked exception so every caller must decide how to handle it
    void transfer(String fromId, String toId, BigDecimal amount) throws InsufficientFundsException {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        Account from = find(fromId);
        Account to = find(toId);

        if (from.balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(fromId, from.balance, amount);
        }
        from.balance = from.balance.subtract(amount);
        to.balance = to.balance.add(amount);
    }

    private Account find(String id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }
}

public class CustomExceptionDemo {
    public static void main(String[] args) {
        TransferService service = new TransferService();
        service.open(new Account("ACC-1", new BigDecimal("5000.00")));
        service.open(new Account("ACC-2", new BigDecimal("1200.00")));

        // Business case 1: the transfer succeeds, then a second one exceeds the balance
        try {
            service.transfer("ACC-1", "ACC-2", new BigDecimal("1500.00"));
            System.out.println("Transfer 1 succeeded");

            service.transfer("ACC-2", "ACC-1", new BigDecimal("3000.00"));
            System.out.println("Transfer 2 succeeded");
        } catch (InsufficientFundsException e) {
            // The caller uses the exception's data to respond meaningfully
            System.out.println("Declined: " + e.getMessage());
            System.out.println("Shortfall: " + e.getShortfall());
        }

        // Business case 2: an unknown account is caught as a runtime error
        try {
            service.transfer("ACC-9", "ACC-1", new BigDecimal("100.00"));
        } catch (InsufficientFundsException e) {
            System.out.println("Unreachable in this case");
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}