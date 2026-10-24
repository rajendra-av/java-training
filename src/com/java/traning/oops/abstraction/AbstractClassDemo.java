package com.java.traning.oops.abstraction;

// 1. Abstract Class: Defines common state and forces a payroll implementation
abstract class Employee {
    private final String id;
    private final String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Concrete method: Common business logic shared by ALL employees
    public void generatePaySlip() {
        System.out.println("=================================");
        System.out.println("PAY SLIP FOR: " + name + " (ID: " + id + ")");
        System.out.println("Net Salary: $" + calculatePay()); // Calls the abstract method
        System.out.println("=================================");
    }

    // Abstract method: Each employment type MUST implement its own pay formula
    public abstract double calculatePay();

    public String getName() {
        return name;
    }
}

// 2. Concrete Subclass: Salaried Full-Time Employee
class FullTimeEmployee extends Employee {
    private final double monthlySalary;
    private final double bonus;

    public FullTimeEmployee(String id, String name, double monthlySalary, double bonus) {
        super(id, name);
        this.monthlySalary = monthlySalary;
        this.bonus = bonus;
    }

    @Override
    public double calculatePay() {
        return monthlySalary + bonus;
    }
}

// 3. Concrete Subclass: Contractor / Hourly Worker
class HourlyEmployee extends Employee {
    private final double hourlyRate;
    private final double hoursWorked;

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculatePay() {
        // Standard pay + 1.5x overtime for hours above 160
        if (hoursWorked > 160) {
            double regularPay = 160 * hourlyRate;
            double overtimePay = (hoursWorked - 160) * (hourlyRate * 1.5);
            return regularPay + overtimePay;
        }
        return hoursWorked * hourlyRate;
    }
}

// 4. Execution
public class AbstractClassDemo {
    public static void main(String[] args) {
        // Create payroll list of different employee types
        Employee[] payrollList = {
                new FullTimeEmployee("EMP-101", "Alice Smith", 8500.00, 1200.00),
                new HourlyEmployee("EMP-102", "Bob Jones", 45.00, 170.0) // 10 hrs overtime
        };

        // Polymorphic payroll processing
        for (Employee emp : payrollList) {
            // Shared template method handles formatting, dynamic dispatch handles calculation
            emp.generatePaySlip();
        }
    }
}