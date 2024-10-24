package com.gui;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private List<Course> enrolledCourses;
    private int grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.grades = 0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getGrades() {
        return this.grades;
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void assignGrade(int grade) {
        this.grades = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}