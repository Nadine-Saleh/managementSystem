package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.facade.ERPSystemFacade;
import com.project.managementsystem.erp.models.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AddSupplier {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    // Use the facade
    private final ERPSystemFacade erpFacade = new ERPSystemFacade();

    @FXML
    public void initialize() {
        // Set action on button click
        saveButton.setOnAction(event -> handleSaveSupplier());
    }

    @FXML
    private void handleSaveSupplier() {
        try {
            // Create supplier from UI input
            Supplier supplier = new Supplier();
            supplier.setName(nameField.getText());
            supplier.setEmail(emailField.getText());
            supplier.setPhone(phoneField.getText());

            // Save via facade
            erpFacade.addSupplier(supplier);

            // Show success alert
            showAlert("Success", "Supplier added successfully!", AlertType.INFORMATION);
            clearForm();

        } catch (IllegalArgumentException e) {
            // Input validation failed
            showAlert("Input Error", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            // Unexpected error
            showAlert("Error", "Failed to add supplier.", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
    }
}