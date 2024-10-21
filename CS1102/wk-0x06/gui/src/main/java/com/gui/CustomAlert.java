package com.gui;

import javafx.scene.control.*;


public class CustomAlert {

    public void showAlert(String message) {
        showAlert(message, Alert.AlertType.ERROR); 
    }
    
     public void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
