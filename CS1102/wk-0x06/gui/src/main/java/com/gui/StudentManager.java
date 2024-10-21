package com.gui;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private List<Course> courses;
    private CustomAlert alert;

    public StudentManager() {
        students = new ArrayList<Student>();
        courses = new ArrayList<Course>();
        alert = new CustomAlert();
    }

    public boolean addStudent(Student newStudent) {
        String newStudentId = newStudent.getId();
        boolean exists = false;

        for (Student student : students) {
            if (student.getId().equals(newStudentId)) {
                exists = true;
                break;
            }
        }

        if (exists) {
           // throw new Error("Studet with this ID already")
           alert.showAlert("Student with this ID already exists");
           return false;
        } else {
            System.out.println("Adding student");
            students.add(newStudent);
            System.out.println("Student added: " + newStudent.getName());
            return true;
        }
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

    public void enrollStudentInCourse(Student student, Course course) {
        student.enrollInCourse(course);
    }

    public void assignGrade(Student student, Course course, String grade) {
        student.assignGrade(course, grade);
    }
}
