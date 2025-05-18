package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.factory.FxmlLoaderUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import javafx.fxml.FXML;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Home {

    @FXML
    private Button addCustomerbtn;
    @FXML
    private Button addProductbtn;
    @FXML
    private Button addPaymentbtn;
    @FXML
    private Button notificationBtn;

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

    @FXML
    private Button purchaseMenu;

    @FXML
    private Button salesBtn;

    @FXML
    void openPurchaseMenu(ActionEvent event) {
        FxmlLoaderUtil.openOptionsMenuWindow(
                getClass(),
                "fxml/options-menu.fxml",
                "Purchase options",
                "purchase"
        );
    }

    @FXML
    void openSalesMenu(ActionEvent event) {
        FxmlLoaderUtil.openOptionsMenuWindow(
                getClass(),
                "fxml/options-menu.fxml",
                "Sales Options",
                "sales"
        );
    }

    @FXML
    private Button inventoryBtn;

    @FXML
    void openInventoryView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inventory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inventory");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showNotifications() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notifications.fxml")); // تأكدي من الاسم والمسار
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Notifications");
            stage.setScene(new Scene(root));
            stage.setWidth(420);
            stage.setHeight(300);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
