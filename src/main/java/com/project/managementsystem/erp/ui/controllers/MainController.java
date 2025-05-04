package com.project.managementsystem.erp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    public MainController() {

    }

    @FXML
    private Label messageLabel;



    /**
     * Handles "Products" menu item click
     */
    public void sayhi() {
messageLabel.setText("hi");
    }

    /**
     * Handles "Customers" menu item click
     */
    public void showCustomers() {
        // TODO: Load CustomerView.fxml into contentArea
        System.out.println("Loading Customers...");
    }

    /**
     * Handles "Exit" menu item click
     */
    public void handleExit() {
        System.exit(0);
    }
}