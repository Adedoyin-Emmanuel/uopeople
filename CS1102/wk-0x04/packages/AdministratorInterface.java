package packages;

import java.util.Scanner;

public class AdministratorInterface {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    enrollStudent();
                    break;
                case 3:
                    assignGrade();
                    break;
                case 4:
                    calculateOverallGrade();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- Course Enrollment and Grade Management System ---");
        System.out.println("1. Add a new course");
        System.out.println("2. Enroll a student");
        System.out.println("3. Assign a grade");
        System.out.println("4. Calculate overall grade");
        System.out.println("5. Exit");
    }

    private static void addNewCourse() {
        String courseCode = getStringInput("Enter course code: ");
        String courseName = getStringInput("Enter course name: ");
        int maxCapacity = getIntInput("Enter maximum capacity: ");
        CourseManagement.addCourse(courseCode, courseName, maxCapacity);
        System.out.println("Course added successfully.");
    }

    private static void enrollStudent() {
        String studentName = getStringInput("Enter student name: ");
        String studentId = getStringInput("Enter student ID: ");
        String courseCode = getStringInput("Enter course code: ");

        Student student = new Student(studentName, studentId);
        Course course = findCourse(courseCode);

        if (course != null) {
            CourseManagement.enrollStudent(student, course);
            System.out.println("Student enrolled successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void assignGrade() {
        String studentId = getStringInput("Enter student ID: ");
        String courseCode = getStringInput("Enter course code: ");
        double grade = getDoubleInput("Enter grade: ");

        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);

        if (student != null && course != null) {
            CourseManagement.assignGrade(student, course, grade);
            System.out.println("Grade assigned successfully.");
        } else {
            System.out.println("Student or course not found.");
        }
    }

    private static void calculateOverallGrade() {
        String studentId = getStringInput("Enter student ID: ");
        Student student = findStudent(studentId);


        if (student != null) {
            double overallGrade = CourseManagement.calculateOverallGrade(student);
            System.out.printf("Overall grade for student %s: %.2f%n", student.getName(), overallGrade);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Course findCourse(String courseCode) {
        for (Course course : CourseManagement.getCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudent(String studentId) {
        return CourseManagement.findStudentById(studentId);
    }
}