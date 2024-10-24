package project;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeManagement {

    public static void main(String[] args) {
        // Sample dataset
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 28, "HR", 50000),
                new Employee("Bob", 35, "IT", 60000),
                new Employee("Charlie", 30, "Finance", 70000),
                new Employee("Diana", 45, "Marketing", 80000),
                new Employee("Ethan", 32, "IT", 75000)
        );

        // Using Function interface to create a function that concatenates name and department
        Function<Employee, String> employeeInfo = emp -> emp.getName() + " - " + emp.getDepartment();

        // Generating a new collection of concatenated strings using streams
        List<String> employeeDetails = employees.stream()
                .map(employeeInfo) // Apply the Function
                .collect(Collectors.toList());

        // Printing employee details
        System.out.println("Employee Details:");
        employeeDetails.forEach(System.out::println);

        // Calculate the average salary using streams
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary) // Extract salaries
                .average() // Calculate average
                .orElse(0); // Default value if no employees

        System.out.println("Average Salary: " + averageSalary);

        // Filtering employees whose age is above 30
        List<String> filteredEmployeeDetails = employees.stream()
                .filter(emp -> emp.getAge() > 30) // Filter based on age
                .map(employeeInfo) // Apply the Function
                .collect(Collectors.toList());

        // Printing filtered employee details
        System.out.println("Employees above 30:");
        filteredEmployeeDetails.forEach(System.out::println);
    }
}
