package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.facade.ERPSystemFacade;
import com.project.managementsystem.erp.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProductController {

    // FXML Fields
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;
    @FXML private ComboBox<String> comboCategory;
    @FXML private ComboBox<String> comboUnit;

    @FXML private Button btnSave;
    @FXML
    public void initialize() {
        // Initialize Category ComboBox
        comboCategory.getItems().addAll(
                "Electronics",
                "Furniture",
                "Stationery",
                "Clothing",
                "Food"
        );
        comboCategory.setValue("Stationery"); // Default selection

        // Initialize Unit ComboBox
        comboUnit.getItems().addAll(
                "Piece",
                "Kg",
                "Gram",
                "Pack",
                "Box"
        );
        comboUnit.setValue("Piece"); // Default selection
    }

    // Facade instance
    private final ERPSystemFacade erp = new ERPSystemFacade();

    /**
     * Event handler for Save button click.
     */
    @FXML

    public void onSaveClick(ActionEvent event) {
        try {
            // Validate inputs
            if (txtName.getText().trim().isEmpty()) {
                showAlert("Input Error", "Product name cannot be empty.");
                return;
            }

            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            if (price <= 0 || quantity < 0) {
                showAlert("Input Error", "Price and quantity must be valid numbers.");
                return;
            }

            String selectedCategory = comboCategory.getValue();
            String selectedUnit = comboUnit.getValue();

            if (selectedCategory == null || selectedUnit == null) {
                showAlert("Selection Required", "Please select a category and unit.");
                return;
            }

            // Build Product object
            Product product = new Product(
                    txtName.getText().trim(),
                    price,
                    quantity,
                    selectedCategory,
                    selectedUnit,
                    System.currentTimeMillis() / 1000 + "" // Simple timestamp string
            );

            // Save using Facade
            erp.addProduct(product);

            showAlert("Success", "Product added successfully!");

            // Clear form and close window
            clearForm();
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Price and Quantity.");
        }
    }

    /**
     * Shows an alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears all form fields.
     */
    private void clearForm() {
        txtName.clear();
        txtPrice.setText("0");
        txtQuantity.setText("0");
        comboCategory.getSelectionModel().clearSelection();
        comboUnit.getSelectionModel().clearSelection();
    }

    /**
     * Closes the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}