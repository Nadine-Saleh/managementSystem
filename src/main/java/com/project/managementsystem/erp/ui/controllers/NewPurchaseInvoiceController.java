package com.project.managementsystem.erp.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NewPurchaseInvoiceController {

    @FXML
    private Button addRowButton;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TextField amountField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField creditField;

    @FXML
    private Label currentBalanceLabel;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<?> invoiceTable;

    @FXML
    private TextField paidField;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TextField priceField;

    @FXML
    private TableColumn<?, ?> productCol;

    @FXML
    private ComboBox<?> productComboBox;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TextField quantityField;

    @FXML
    private TableColumn<?, ?> removeCol;

    @FXML
    private Button saveButton;

    @FXML
    private Label totalLabel;

    @FXML
    void handleAddRow(ActionEvent event) {

    }

    @FXML
    void handleCancel(ActionEvent event) {

    }

    @FXML
    void handleSaveInvoice(ActionEvent event) {

    }

}
