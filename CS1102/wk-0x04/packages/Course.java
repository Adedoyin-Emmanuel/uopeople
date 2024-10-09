package packages;


public class Course {
    private String courseCode;
    private String name;
    private int maxCapacity;
    private static int totalEnrolledStudents = 0;

    public Course(String courseCode, String name, int maxCapacity) {
        this.courseCode = courseCode;
        this.name = name;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public void incrementEnrolledStudents() {
        if (totalEnrolledStudents < maxCapacity) {
            totalEnrolledStudents++;
        }
    }
}
