package com.project.managementsystem.erp.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class SalesInvoicesController {

    @FXML
    private Button DwonloadBtn;

    @FXML
    private TableColumn<?, ?> invoiceCustomer;

    @FXML
    private TableColumn<?, ?> invoiceDate;

    @FXML
    private TableColumn<?, ?> invoiceNum;

    @FXML
    private TableColumn<?, ?> invoiceTotalAmount;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchByCustomerName;

    @FXML
    void downloadCSV(ActionEvent event) {

    }

    @FXML
    void getInvoiceNumber(ActionEvent event) {

    }

    @FXML
    void onSearchClick(ActionEvent event) {

    }

}
