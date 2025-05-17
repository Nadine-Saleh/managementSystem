package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.AdminDAOImpl;
import com.project.managementsystem.erp.models.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final AdminDAOImpl adminDAO = new AdminDAOImpl();

    @FXML
    private void handleSignUp() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("❗ Please fill in all fields.");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("❗ Please enter a valid email address.");
            return;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*\\d).{6,}$")) {
            showAlert("❗ Password must be at least 6 characters,\ncontain an uppercase letter and a number.");
            return;
        }

        Admin admin = new Admin(name, username, email, password);
        boolean success = adminDAO.register(admin);

        if (success) {
            showAlert("🎉 Sign up successful! Please log in.");
            goToLogin(); // ← هنا بنستدعي الميثود اللي تحوّل للـ Login
        } else {
            showAlert("❌ Sign up failed. Username might already exist.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("ERP - Login");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("❌ Failed to open login page.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        nameField.clear();
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}
