package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.facade.ERPSystemFacade;
import com.project.managementsystem.erp.models.Customer;
import com.project.managementsystem.erp.models.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PaymentController {

    // FXML Fields
    @FXML
    private DatePicker datepickerpay;

    @FXML
    private ComboBox<String> Methodcombopay;

    @FXML
    private ComboBox<String> customernamecombopay;

    @FXML
    private TextField amountfieldpay; // Corrected typo

    @FXML
    private Button savebuttonpay;

    // Service Facade
    private final ERPSystemFacade erpFacade = new ERPSystemFacade();

    private List<Customer> allCustomers;

    @FXML
    public void initialize() {
        setupMethodComboBox();
        setupCustomerComboBox();
        setupSaveButtonAction();
    }

    /**
     * Setup available payment methods.
     */
    private void setupMethodComboBox() {
        Methodcombopay.getItems().addAll("Cash", "Credit Card", "Bank Transfer", "Check");
    }

    /**
     * Load all customer names into the combo box.
     */
    private void setupCustomerComboBox() {
        allCustomers = erpFacade.getAllCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            customerNames.add(customer.getName());
        }

        customernamecombopay.setItems(customerNames);
    }

    /**
     * Handle Save button click.
     */
    private void setupSaveButtonAction() {
        savebuttonpay.setOnAction(event -> {
            try {
                // Validate inputs
                if (datepickerpay.getValue() == null) {
                    showAlert("Input Error", "Please select a payment date.", AlertType.WARNING);
                    return;
                }

                String selectedMethod = Methodcombopay.getValue();
                if (selectedMethod == null || selectedMethod.isEmpty()) {
                    showAlert("Input Error", "Please select a payment method.", AlertType.WARNING);
                    return;
                }

                String selectedCustomerName = customernamecombopay.getValue();
                if (selectedCustomerName == null || selectedCustomerName.isEmpty()) {
                    showAlert("Input Error", "Please select a customer.", AlertType.WARNING);
                    return;
                }

                String amountText = amountfieldpay.getText();
                double amount;
                try {
                    amount = Double.parseDouble(amountText);
                    if (amount <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter a valid positive amount.", AlertType.WARNING);
                    return;
                }

                // Find selected customer
                Customer selectedCustomer = null;
                for (Customer c : allCustomers) {
                    if (c.getName().equals(selectedCustomerName)) {
                        selectedCustomer = c;
                        break;
                    }
                }

                if (selectedCustomer == null) {
                    showAlert("Input Error", "Invalid customer selected.", AlertType.WARNING);
                    return;
                }
                //Handling date
                LocalDate selectedDate = datepickerpay.getValue();

                LocalDate sqlDate = selectedDate;

                // Create payment object
                Payment payment = new Payment();
                payment.setPaymentDate(sqlDate);
                payment.setMethod(selectedMethod);
                payment.setStatus("Completed"); // Default status
                payment.setAmount(amount);
                payment.setInvoiceId(1); // Placeholder - replace later with real invoice logic
                payment.setReference("REF-" + System.currentTimeMillis());

                // Save using facade
                erpFacade.addPayment(payment);

                // Success
                showAlert("Success", "Payment added successfully!", AlertType.INFORMATION);
                clearForm();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add payment.", AlertType.ERROR);
            }
        });
    }

    /**
     * Shows an alert dialog.
     */
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears form after successful save.
     */
    private void clearForm() {
        datepickerpay.setValue(null);
        Methodcombopay.setValue(null);
        customernamecombopay.setValue(null);
        amountfieldpay.clear();
    }
}