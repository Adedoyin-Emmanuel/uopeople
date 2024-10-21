package com.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUI {
    private StudentManager studentManager;
    private ObservableList<Student> studentObservableList;  
    private ObservableList<Course> courseObservableList;    

    private CustomAlert alert;

    public GUI() {
        studentManager = new StudentManager();
        studentObservableList = FXCollections.observableArrayList(studentManager.getStudents());  
        courseObservableList = FXCollections.observableArrayList(studentManager.getCourses());   
        alert = new CustomAlert();
    }

    public TabPane getLayout() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab studentTab = new Tab("Student Management");
        Tab enrollmentTab = new Tab("Course Registration");
        Tab gradeTab = new Tab("Grade Management");
        Tab courseTab = new Tab("Course Management");

        studentTab.setContent(createStudentManagementTab());
        enrollmentTab.setContent(createCourseRegistrationTab());
        gradeTab.setContent(createGradeManagementTab());
        courseTab.setContent(createCourseManagementTab());

        tabPane.getTabs().addAll(studentTab, enrollmentTab, gradeTab, courseTab);

        return tabPane;
    }

    private VBox createStudentManagementTab() {

        VBox studentLayout = new VBox(10);
        studentLayout.setPadding(new Insets(10));

        Label instructionLabel = new Label("Manage students by adding, viewing, or editing their information.");
        
        TextField nameField = new TextField();
        TextField idField = new TextField();  

        Button addButton = new Button("Add Student");

        
        TableView<Student> studentTable = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");

        
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        studentTable.getColumns().addAll(nameColumn, idColumn);
        studentTable.setItems(studentObservableList);
            


        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String id = idField.getText(); 
            if (!name.isEmpty() && !id.isEmpty()) {
                Student student = new Student(name, id);
                if (studentManager.addStudent(student)) {
                    studentObservableList.add(student);  
                    nameField.clear();
                    idField.clear();
                }
            } else {
                alert.showAlert("Please enter valid student details");
            }
        });

        studentLayout.getChildren().addAll(
            instructionLabel, 
            new Label("Student Name:"), 
            nameField, 
            new Label("Student ID:"), 
            idField,  
            addButton, 
            studentTable
        );

        return studentLayout;
    }


    private VBox createCourseRegistrationTab() {
        VBox enrollmentLayout = new VBox(10);
        enrollmentLayout.setPadding(new Insets(10));

        Label instructionLabel = new Label("Enroll students in courses by selecting the student and course from the lists below.");
        ChoiceBox<Student> studentChoiceBox = new ChoiceBox<>(studentObservableList); 
        ChoiceBox<Course> courseChoiceBox = new ChoiceBox<>(courseObservableList);    
        Button enrollButton = new Button("Enroll in Course");

        enrollButton.setOnAction(event -> {
            Student selectedStudent = studentChoiceBox.getValue();
            Course selectedCourse = courseChoiceBox.getValue();
            if (selectedStudent != null && selectedCourse != null) {
                studentManager.enrollStudentInCourse(selectedStudent, selectedCourse);
                alert.showAlert("Student enrolled in course", Alert.AlertType.INFORMATION);
            } else {
                alert.showAlert("Select a student and a course");
            }
        });

        enrollmentLayout.getChildren().addAll(instructionLabel, new Label("Select Student:"), studentChoiceBox, new Label("Select Course:"), courseChoiceBox, enrollButton);

        return enrollmentLayout;
    }

    private VBox createGradeManagementTab() {
        VBox gradeLayout = new VBox(10);
        gradeLayout.setPadding(new Insets(10));

        Label instructionLabel = new Label("Assign grades to students. Select a student and enter their grade.");
        ChoiceBox<Student> studentChoiceBox = new ChoiceBox<>(studentObservableList); 
        TextField gradeField = new TextField();
        Button assignGradeButton = new Button("Assign Grade");

        assignGradeButton.setOnAction(event -> {
            Student selectedStudent = studentChoiceBox.getValue();
            String grade = gradeField.getText();
            if (selectedStudent != null && !grade.isEmpty()) {
                studentManager.assignGrade(selectedStudent, grade);
                alert.showAlert("Grade assigned to student", Alert.AlertType.INFORMATION);
            } else {
                alert.showAlert("Please select a student and enter a valid grade");
            }
        });

        gradeLayout.getChildren().addAll(instructionLabel, new Label("Select Student:"), studentChoiceBox, new Label("Enter Grade:"), gradeField, assignGradeButton);

        return gradeLayout;
    }

    private VBox createCourseManagementTab() {
        VBox courseLayout = new VBox(10);
        courseLayout.setPadding(new Insets(10));

        Label instructionLabel = new Label("Manage courses by adding new courses.");
        TextField courseNameField = new TextField();
        TextField courseIdField = new TextField();
        Button addCourseButton = new Button("Add Course");

        TableView<Course> courseTable = new TableView<>();
        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        TableColumn<Course, String> courseIdColumn = new TableColumn<>("Course ID");

        courseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseName()));
        courseIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseId()));


        courseTable.getColumns().addAll(courseNameColumn, courseIdColumn);
        courseTable.setItems(courseObservableList);
        

        addCourseButton.setOnAction(event -> {
            String courseName = courseNameField.getText();
            String courseId = courseIdField.getText();
            if (!courseName.isEmpty() && !courseId.isEmpty()) {
                Course course = new Course(courseName, courseId);
                studentManager.addCourse(course);  
                courseObservableList.add(course);  
                courseNameField.clear();
                courseIdField.clear();
            } else {
                alert.showAlert("Please enter valid course details");
            }
        });

        courseLayout.getChildren().addAll(instructionLabel, new Label("Course Name:"), courseNameField, new Label("Course ID:"), courseIdField, addCourseButton, courseTable);

        return courseLayout;
    }
}
