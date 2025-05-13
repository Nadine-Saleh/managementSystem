package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.facade.ERPSystemFacade;
import com.project.managementsystem.erp.models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AddCustomerController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField balanceField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    // Use the facade
    private final ERPSystemFacade erpFacade = new ERPSystemFacade();

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> handleSaveCustomer());
    }
    

    @FXML
    private void handleSaveCustomer() {
        try {
            // Create customer from UI input
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setEmail(emailField.getText());
            customer.setBalance(balanceField.getText());
            customer.setPhone(phoneField.getText());

            // Save via facade
            erpFacade.addCustomer(customer);

            // Show success alert
            showAlert("Success", "Customer added successfully!", AlertType.INFORMATION);
            clearForm();

        } catch (IllegalArgumentException e) {
            // Input validation failed
            showAlert("Input Error", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            // Unexpected error
            showAlert("Error", "Failed to add customer.", AlertType.ERROR);
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
        balanceField.clear();
        phoneField.clear();
    }
}