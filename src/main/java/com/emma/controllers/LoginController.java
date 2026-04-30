package com.emma.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML 
    private Button buttonLogin;

    @FXML
    private void handleLogin() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        
        

        String user = usernameField.getText();
        String pass = passwordField.getText();

        // login fake por ahora
        if ("admin".equals(user) && "1234".equals(pass)) {
           
            try {
                 
                 FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/main.fxml")
                );

                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) usernameField.getScene().getWindow();

                stage.setTitle("Libros");
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
           
                e.printStackTrace();
            }

         //System.out.println("Login correcto ✅");
        } else {
            alert.setTitle("Login Fallido");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales inválidas ❌");
            alert.showAndWait();
            //messageLabel.setText("Credenciales inválidas ❌");
            //System.out.println("Credenciales inválidas ❌");
        }
    }
}
