package com.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;

public class GUI {
    private StudentManager studentManager;
    private VBox layout;
    private TableView<Student> studentTable;
    
    public GUI() {
        studentManager = new StudentManager();
        layout = new VBox(10);
        layout.setPadding(new Insets(10));
        createComponents();
    }

    public VBox getLayout() {
        return layout;
    }

    private void createComponents() {
        // Labels and TextFields for student details
        TextField nameField = new TextField();
        TextField idField = new TextField();
        Button addButton = new Button("Add Student");

        // Table to display students
        studentTable = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        
        studentTable.getColumns().addAll(nameColumn, idColumn);

        // Add Student button event handler
        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String id = idField.getText();
            if (!name.isEmpty() && !id.isEmpty()) {
                Student student = new Student(name, id);
                studentManager.addStudent(student);
                studentTable.getItems().add(student);
                nameField.clear();
                idField.clear();
            } else {
                showAlert("Please enter valid details");
            }
        });

        // Layout organization
        layout.getChildren().addAll(new Label("Name:"), nameField, new Label("ID:"), idField, addButton, studentTable);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }
}
