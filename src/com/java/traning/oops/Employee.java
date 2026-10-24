package com.java.traning.oops;

// A class is a blueprint, which defines what data it represents and the behavior an employee has (fields)
// No memory is allocated for an employee until an object is created from the class.
public class Employee {

    // Fields (instance variables): each Employee object gets its own copy.
    // Changing one employee's name does not affect any other employee.
    String name;
    String department;
    double salary;

    // Static field: belongs to the class itself, shared by all employees.
    // Only one copy exists, no matter how many objects are created.
    static int totalEmployees = 0;

    // Constructor: a special method that runs when an object is created.
    // It initializes the new object's fields.
    public Employee(String name, String department, double salary) {
        this.name = name;              // "this" refers to the current object
        this.department = department;
        this.salary = salary;
        totalEmployees++;              // count every object created
    }

    // Instance method: operates on the data of one particular object.
    public void giveRaise(double percent) {
        salary = salary + (salary * percent / 100);
    }

    // Instance method that returns a description of this object's state.
    public String describe() {
        return name + " works in " + department + " earning " + salary;
    }

    public static void main(String[] args) {
        // Object instantiation: "new" creates an object and allocates memory for fields
        // Objects are stored in heap memory.
        // emp1 and emp2 are references for two different object being created
        Employee emp1 = new Employee("Asha", "Engineering", 80000);
        Employee emp2 = new Employee("Ravi", "Finance", 65000);

        // Each object has its own state.
        System.out.println(emp1.describe());   // Asha works in Engineering earning 80000.0
        System.out.println(emp2.describe());   // Ravi works in Finance earning 65000.0

        // Calling an instance method on one object changes only that object.
        emp1.giveRaise(10);
        System.out.println(emp1.describe());   // Asha works in Engineering earning 88000.0
        System.out.println(emp2.describe());   // Ravi works in Finance earning 65000.0 (unchanged)

        // Static members have class scope and accessed through the class name
        // No object instance is needed to access static members.
        System.out.println("Total employees: " + Employee.totalEmployees);   // 2
    }
}
