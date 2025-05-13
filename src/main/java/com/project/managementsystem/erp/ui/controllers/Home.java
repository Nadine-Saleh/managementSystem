package com.project.managementsystem.erp.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    @FXML
    private Button addCustomerbtn;
    @FXML
    private Button addProductbtn;
    @FXML
    private Button addPaymentbtn;

    @FXML
    private AnchorPane mainContainer; // This is from home.fxml

    @FXML
    public void initialize() {
        addCustomerbtn.setOnAction(event -> showCustomerForm());
        addProductbtn.setOnAction(event -> showproductForm());
        addPaymentbtn.setOnAction(event -> showpPaymentForm());
    }

    private void showCustomerForm() {
        try {
            // Load customer.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer.fxml"));
            Parent customerRoot = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Customer Form");
            stage.setScene(new Scene(customerRoot));

            // Make it modal (blocks interaction with main window)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it's closed
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading customer.fxml");
            e.printStackTrace();
        }
    }
    private void showproductForm() {
        try {
            // Load customer.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductView.fxml"));
            Parent ProductViewrRoot = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Customer Form");
            stage.setScene(new Scene(ProductViewrRoot));

            // Make it modal (blocks interaction with main window)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it's closed
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading ProductView.fxml");
            e.printStackTrace();
        }
    }
    //Adding Payment
    private void showpPaymentForm() {
        try {
            // Load customer.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/payment.fxml"));
            Parent paymentRoot = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Customer Form");
            stage.setScene(new Scene(paymentRoot));

            // Make it modal (blocks interaction with main window)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it's closed
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading ProductView.fxml");
            e.printStackTrace();
        }
    }
}