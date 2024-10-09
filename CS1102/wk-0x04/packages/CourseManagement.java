package packages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManagement {
    private static List<Course> courses = new ArrayList<>();
    private static Map<Student, Map<Course, Double>> studentGrades = new HashMap<>();

    public static void addCourse(String courseCode, String name, int maxCapacity) {
        Course course = new Course(courseCode, name, maxCapacity);
        courses.add(course);
    }

    public static void enrollStudent(Student student, Course course) {
        if (courses.contains(course)) {
            student.enrollCourse(course);
            studentGrades.putIfAbsent(student, new HashMap<>());
        }
    }

    public static void assignGrade(Student student, Course course, double grade) {
        if (studentGrades.containsKey(student)) {
            student.assignGrade(course, grade);
            studentGrades.get(student).put(course, grade);
        }
    }

    public static double calculateOverallGrade(Student student) {
        if (studentGrades.containsKey(student)) {
            Map<Course, Double> grades = studentGrades.get(student);
            if (grades.isEmpty()) {
                return 0.0;
            }
            double sum = grades.values().stream().mapToDouble(Double::doubleValue).sum();
            return sum / grades.size();
        }
        return 0.0;
    }

    public static List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public static Student findStudentById(String studentId) {
    for (Student student : studentGrades.keySet()) {
        if (student.getId().equals(studentId)) {
            return student;
        }
    }
    return null;
}
}