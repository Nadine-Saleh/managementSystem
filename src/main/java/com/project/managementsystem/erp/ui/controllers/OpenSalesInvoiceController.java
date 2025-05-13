package com.project.managementsystem.erp.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class OpenSalesInvoiceController {

    @FXML
    private Label SalesINuum;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private Label customerName;

    @FXML
    private Label invoiceDate;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private Button printButton;

    @FXML
    private TableColumn<?, ?> productColumn;

    @FXML
    private TableColumn<?, ?> quantitiColumn;

    @FXML
    private Label totalPrice;

    @FXML
    void downloaddocument(ActionEvent event) {

    }

}
