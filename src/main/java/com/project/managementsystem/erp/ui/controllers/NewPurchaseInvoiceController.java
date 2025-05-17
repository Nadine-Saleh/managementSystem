package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.CustomerDAO;
import com.project.managementsystem.erp.dao.CustomerDAOImpl;
import com.project.managementsystem.erp.dao.ProductDAO;
import com.project.managementsystem.erp.dao.ProductDAOImpl;
import com.project.managementsystem.erp.models.Customer;
import com.project.managementsystem.erp.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPurchaseInvoiceController implements Initializable {

    @FXML private Button addRowButton;
    @FXML private TableColumn<?, ?> amountCol;
    @FXML private TextField amountField;
    @FXML private Button cancelButton;
    @FXML private TextField creditField;
    @FXML private Label currentBalanceLabel;
    @FXML private ComboBox<Customer> customerComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TableView<?> invoiceTable;
    @FXML private TextField paidField;
    @FXML private TableColumn<?, ?> priceCol;
    @FXML private TextField priceField;
    @FXML private TableColumn<?, ?> productCol;
    @FXML private ComboBox<Product> productComboBox;
    @FXML private TableColumn<?, ?> quantityCol;
    @FXML private TextField quantityField;
    @FXML private TableColumn<?, ?> removeCol;
    @FXML private Button saveButton;
    @FXML private Label totalLabel;

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Customer> customers = FXCollections.observableArrayList(customerDAO.getAllCustomers());
        customerComboBox.setItems(customers);

        ObservableList<Product> products = FXCollections.observableArrayList(productDAO.getAll());
        productComboBox.setItems(products);
    }

    @FXML
    void handleAddRow(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void handleCancel(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void handleSaveInvoice(ActionEvent event) {
        // To be implemented
    }
}
