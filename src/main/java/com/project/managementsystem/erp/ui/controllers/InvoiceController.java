package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.services.InvoiceService;
import com.project.managementsystem.erp.dao.*;
import com.project.managementsystem.erp.models.*;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceController {

    @FXML private ComboBox<String> customerComboBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Product> productComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField amountField;
    @FXML private Button addRowButton;
    @FXML private TableView<LineItem> invoiceTable;
    @FXML private TableColumn<LineItem, String> productCol;
    @FXML private TableColumn<LineItem, Integer> quantityCol;
    @FXML private TableColumn<LineItem, Double> priceCol;
    @FXML private TableColumn<LineItem, Double> amountCol;
    @FXML private TableColumn<LineItem, Void> removeCol;
    @FXML private Label totalLabel;
    @FXML private TextField creditField;
    @FXML private TextField paidField;
    @FXML private Label currentBalanceLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private InvoiceService invoiceService = new InvoiceService(new InvoiceDAOImpl(), new LineItemDAOImpl(), new InventoryDAOImpl());
    private ObservableList<LineItem> lineItems = FXCollections.observableArrayList();
    private List<Customer> customers = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @FXML
    public void initialize() {
        setupCustomerComboBox();
        setupProductComboBox();
        setupInvoiceTable();
        setupListeners();
    }

    private void setupCustomerComboBox() {
        customers = customerDAO.getAllCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            customerNames.add(customer.getName());
        }
        customerComboBox.setItems(customerNames);
    }

    private void setupProductComboBox() {
        products = productDAO.getAll();
        ObservableList<Product> productList = FXCollections.observableArrayList(products);
        productComboBox.setItems(productList);
        productComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Product product) {
                return product != null ? product.getName() : "";
            }

            @Override
            public Product fromString(String string) {
                return products.stream()
                        .filter(product -> product.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void setupInvoiceTable() {
        productCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getUnitPrice()).asObject());
        amountCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());

        removeCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("X");

            {
                btn.setOnAction(event -> {
                    LineItem item = getTableView().getItems().get(getIndex());
                    lineItems.remove(item);
                    updateTotals();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        invoiceTable.setItems(lineItems);
    }

    private void setupListeners() {
        productComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                priceField.setText(String.format("%.2f", newVal.getPrice()));
            }
        });

        quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                int qty = Integer.parseInt(newVal);
                Product product = productComboBox.getValue();
                if (product != null && qty > 0) {
                    double amount = product.getPrice() * qty;
                    amountField.setText(String.format("%.2f", amount));
                }
            } catch (NumberFormatException ignored) {}
        });
    }

    @FXML
    private void handleAddRow(ActionEvent event) {
        Product product = productComboBox.getValue();
        String qtyStr = quantityField.getText();

        if (product == null) {
            showAlert("Missing Product", "Please select a product.");
            return;
        }

        if (qtyStr.isEmpty()) {
            showAlert("Missing Quantity", "Please enter a quantity.");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0 || qty > product.getStock()) {
                showAlert("Invalid Quantity", "Please enter a valid quantity in stock.");
                return;
            }

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(qty);
            lineItem.setTotal(qty * product.getPrice());

            lineItems.add(lineItem);
            product.setStock(product.getStock() - qty); // Deduct from inventory
            clearEntryFields();
            updateTotals();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Quantity must be a valid number.");
        }
    }

    @FXML
    private void handleSaveInvoice(ActionEvent event) {
        String selectedCustomer = customerComboBox.getValue();
        LocalDate date = datePicker.getValue();
        if (selectedCustomer == null || date == null || lineItems.isEmpty()) {
            showAlert("Missing Data", "Please fill all required fields.");
            return;
        }

        try {
            Customer customer = customers.stream()
                    .filter(c -> c.getName().equals(selectedCustomer))
                    .findFirst()
                    .orElse(null);

            if (customer == null) {
                showAlert("Invalid Customer", "Selected customer does not exist.");
                return;
            }

            invoiceService.saveInvoice(customer, date, new ArrayList<>(lineItems),"SALE");
            showAlert("Success", "Invoice saved successfully!");
            clearForm();
        } catch (Exception e) {
            showAlert("Error", "Failed to save the invoice. Please try again.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
    }

    private void clearEntryFields() {
        productComboBox.getSelectionModel().clearSelection();
        quantityField.clear();
        priceField.clear();
        amountField.clear();
    }

    private void clearForm() {
        customerComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        lineItems.clear();
        paidField.clear();
        creditField.clear();
        updateTotals();
    }

    private void updateTotals() {
        double total = lineItems.stream().mapToDouble(LineItem::getTotal).sum();
        totalLabel.setText(String.format("%.2f $", total));

        double paid = parseInput(paidField.getText());
        double credit = parseInput(creditField.getText());
        double balance = total - paid - credit;

        currentBalanceLabel.setText(String.format("%.2f $", balance));
    }

    private double parseInput(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}