package com.gui;

public class Course {
    private String courseName;
    private String courseId;

    public Course(String courseName, String courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }

    // Getters and setters
    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }
}
