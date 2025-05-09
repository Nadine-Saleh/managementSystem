package com.project.managementsystem.erp.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Button customer;

    @FXML
    private ImageView invoiceBtn;

    @FXML
    public void gotoCustomer(ActionEvent event) {
        try {
            // تحميل الـ FXML الخاص بـ customer
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demo3/customer.fxml"));

            Parent customerView = loader.load();

            // تغيير الـ scene إلى صفحة الـ customer
            Scene scene = new Scene(customerView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Customer Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoInvoice(MouseEvent event) {

    }

}
