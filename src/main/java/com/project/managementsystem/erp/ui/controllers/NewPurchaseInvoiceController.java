package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.*;

import com.project.managementsystem.erp.models.*;
import com.project.managementsystem.erp.facade.ERPSystemFacade;

import com.project.managementsystem.erp.services.AddSupplierService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewPurchaseInvoiceController {

    // FXML Fields
    @FXML private ComboBox<Supplier> supplierComboBox;
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
    @FXML private TextField paidField;
    @FXML private TextField creditField;
    @FXML private Label currentBalanceLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    // Services & DAOs
    private final ERPSystemFacade erp = new ERPSystemFacade();

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final SupplierDAO supplierDAO = new AddSupplierService().getSupplierDAO();

    // UI Data Models
    private ObservableList<LineItem> lineItems = FXCollections.observableArrayList();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @FXML
    public void initialize() {
        setupSupplierComboBox();
        setupProductComboBox();
        setupInvoiceTable();
        setupListeners();
    }

    // Setup Supplier ComboBox
    private void setupSupplierComboBox() {
        suppliers = supplierDAO.getAllSuppliers();
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList(suppliers);
        supplierComboBox.setItems(supplierList);

        supplierComboBox.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                return supplier != null ? supplier.getName() : "";
            }

            @Override
            public Supplier fromString(String string) {
                return suppliers.stream()
                        .filter(s -> s.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    // Setup Product ComboBox
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
                        .filter(p -> p.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    // Setup Invoice Table View
    private void setupInvoiceTable() {
        productCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getUnitPrice()).asObject());
        amountCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());

        // Remove Button Column
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

    // Input Listeners
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

    // Handle Add Row Button
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
            if (qty <= 0) {
                showAlert("Invalid Quantity", "Please enter a valid positive quantity.");
                return;
            }

            LineItem lineItem = new LineItem();
            lineItem.setProductId(product.getId());
            lineItem.setProduct(product);
            lineItem.setQuantity(qty);
            lineItem.setTotal(qty * product.getPrice());

            lineItems.add(lineItem);
            clearEntryFields();
            updateTotals();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Quantity must be a number.");
        }
    }

    // Handle Save Button
    @FXML
    private void handleSaveInvoice(ActionEvent event) {
        Supplier selectedSupplier = supplierComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (selectedSupplier == null || date == null || lineItems.isEmpty()) {
            showAlert("Missing Data", "Please fill all required fields.");
            return;
        }

        try {
            erp.createPurchaseInvoice(selectedSupplier, selectedSupplier.getId(), date, lineItems);
            showAlert("Success", "Purchase Invoice saved successfully!");
            clearForm();
        } catch (Exception e) {
            showAlert("Error", "Failed to save purchase invoice.");
            e.printStackTrace(); // Optional: remove in production
        }
    }

    // Handle Cancel Button
    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
    }

    // Helper Methods
    private void clearEntryFields() {
        productComboBox.getSelectionModel().clearSelection();
        quantityField.clear();
        priceField.clear();
        amountField.clear();
    }

    private void clearForm() {
        supplierComboBox.getSelectionModel().clearSelection();
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
        if (value == null || value.isEmpty()) return 0.0;
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