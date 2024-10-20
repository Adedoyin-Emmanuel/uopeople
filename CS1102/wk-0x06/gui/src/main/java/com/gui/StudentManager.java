package com.gui;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private List<Course> courses;

    public StudentManager() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }
}
