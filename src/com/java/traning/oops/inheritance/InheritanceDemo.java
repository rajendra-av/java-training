package com.java.traning.oops.inheritance;

// Superclass (parent): holds state and behavior shared by all employees
class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public double calculatePay() {
        return baseSalary;
    }

    public String describe() {
        return name + " | Pay: " + calculatePay();
    }
}

// Subclass (child): inherits name and baseSalary, and extends the behavior
class Manager extends Employee {
    private double bonus;

    public Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);   // calls the parent constructor first
        this.bonus = bonus;
    }

    // Overrides the parent method with specialized behavior
    @Override
    public double calculatePay() {
        return baseSalary + bonus;
    }
}

// Another subclass: a different specialization of Employee
class Intern extends Employee {
    public Intern(String name, double stipend) {
        super(name, stipend);
    }

    // Keeps the parent's calculatePay() unchanged, so it inherits the behavior
}

public class InheritanceDemo {
    public static void main(String[] args) {
        Employee e = new Employee("Asha", 60000);
        Manager m = new Manager("Ravi", 90000, 15000);
        Intern i = new Intern("Meera", 20000);

        // Each object is used through the parent type (polymorphism)
        Employee[] staff = {e, m, i};
        for (Employee emp : staff) {
            System.out.println(emp.describe());
        }

        // Manager inherits describe() from Employee but uses its own calculatePay()
        System.out.println("Manager is an Employee: " + (m instanceof Employee));
    }
}