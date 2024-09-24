import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a Student with attributes like name, ID, age, and grade.
 */
class Student {
    private String name;
    private int id;
    private int age;
    private String grade;

    public Student(String name, int id, int age, String grade) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void updateInfo(String name, int age, String grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}

/**
 * This class manages the collection of students and provides methods to manipulate student data.
 */
class StudentManagement {
    private static ArrayList<Student> students = new ArrayList<>();
    private static int totalStudents = 0;

    public static void addStudent(String name, int id, int age, String grade) {
        Student student = new Student(name, id, age, grade);
        students.add(student);
        totalStudents++;
        System.out.println("Student added successfully.");
    }

    public static void updateStudent(int id, String name, int age, String grade) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.updateInfo(name, age, grade);
                System.out.println("Student information updated successfully.");
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    public static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static int getTotalStudents() {
        return totalStudents;
    }
}

/**
 * This is the main class that runs the Student Record Management System.
 */
public class StudentRecordManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Record Management System ---");
            System.out.println("1. Add New Student");
            System.out.println("2. Update Student Information");
            System.out.println("3. View Student Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter student age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter student grade: ");
                    String grade = scanner.nextLine();
                    StudentManagement.addStudent(name, id, age, grade);
                    break;

                case 2:
                    System.out.print("Enter student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new student name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new student age: ");
                    int newAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new student grade: ");
                    String newGrade = scanner.nextLine();
                    StudentManagement.updateStudent(updateId, newName, newAge, newGrade);
                    break;

                case 3:
                    System.out.println("\n--- Student Details ---");
                    StudentManagement.viewStudents();
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
        scanner.close();
    }
}
