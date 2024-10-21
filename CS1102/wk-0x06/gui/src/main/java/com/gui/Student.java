package com.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Student {
    private String name;
    private String id;
    private Map<Course, String> enrolledCourses;
    private List<String> grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new HashMap<>();
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Map<Course, String> getEnrolledCourses() {
        return new HashMap<>(enrolledCourses);
    }

    public List<String> getGrades() {
        return grades;
    }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.containsKey(course)) {
            enrolledCourses.put(course, null);
        }
    }


    public void assignGrade(Course course, String grade) {
        if (enrolledCourses.containsKey(course)) {
            enrolledCourses.put(course, grade);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
