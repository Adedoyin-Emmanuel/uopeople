import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


class Employee {
    private String name;
    private int age;
    private String department;
    private double salary;

    public Employee(String name, int age, String department, double salary) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }
}

public class EmployeeManagement {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 28, "HR", 50000),
                new Employee("Bob", 35, "IT", 60000),
                new Employee("Charlie", 30, "Finance", 70000),
                new Employee("Diana", 45, "Marketing", 80000),
                new Employee("Ethan", 32, "IT", 75000)
        );

        Function<Employee, String> employeeInfo = emp -> emp.getName() + " - " + emp.getDepartment();

        List<String> employeeDetails = employees.stream()
                .map(employeeInfo) 
                .collect(Collectors.toList());

        System.out.println("Employee Details:");
        employeeDetails.forEach(System.out::println);

        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary) 
                .average()
                .orElse(0); 

        System.out.println("Average Salary: " + averageSalary);

        List<String> filteredEmployeeDetails = employees.stream()
                .filter(emp -> emp.getAge() > 30) 
                .map(employeeInfo) 
                .collect(Collectors.toList());

        System.out.println("Employees above 30:");
        filteredEmployeeDetails.forEach(System.out::println);
    }
}
