package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.PurchaseInvoiceDAO;
import com.project.managementsystem.erp.dao.PurchaseInvoiceDAOImpl;
import com.project.managementsystem.erp.models.PurchaseInvoice;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PurchaseInvoicesController {

    @FXML private TextField searchBysupplierName;
    @FXML private Button searchBtn;
    @FXML private Button DwonloadBtn;
    @FXML private TableView<PurchaseInvoice> invoicesTable;
    @FXML private TableColumn<PurchaseInvoice, Integer> invoiceNum; // Matches id in PurchaseInvoice
    @FXML private TableColumn<PurchaseInvoice, String> invoiceSupplier;
    @FXML private TableColumn<PurchaseInvoice, String> invoiceDate;
    @FXML private TableColumn<PurchaseInvoice, Double> invoiceTotalAmount;

    private final PurchaseInvoiceDAO purchaseInvoiceDAO = new PurchaseInvoiceDAOImpl();

    /**
     * Called automatically after FXML is loaded.
     * Initialize table columns and load all purchase invoices.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadAllPurchaseInvoices();
    }

    private void setupTableColumns() {
        invoiceNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        invoiceSupplier.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplierName()));
        invoiceDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
        invoiceTotalAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalAmount()).asObject());
    }

    private void loadAllPurchaseInvoices() {
        try {
            List<PurchaseInvoice> purchaseInvoices = purchaseInvoiceDAO.getAll();
            ObservableList<PurchaseInvoice> observableInvoices = FXCollections.observableArrayList(purchaseInvoices);
            invoicesTable.setItems(observableInvoices);
        } catch (Exception e) {
            showAlert("Error", "Failed to load purchase invoices.");
            e.printStackTrace(); // 👈 Add this to see what went wrong
        }
    }

    @FXML
    public void onSearchClick(ActionEvent event) {
        String keyword = searchBysupplierName.getText().trim();

        if (keyword.isEmpty()) {
            loadAllPurchaseInvoices();
            return;
        }

        try {
            List<PurchaseInvoice> results = purchaseInvoiceDAO.searchBySupplierName(keyword);
            invoicesTable.setItems(FXCollections.observableArrayList(results));
        } catch (Exception e) {
            showAlert("Search Error", "Could not search purchase invoices: " + e.getMessage());
        }
    }

    @FXML
    public void downloadCSV(ActionEvent event) {
        List<PurchaseInvoice> currentInvoices = invoicesTable.getItems();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchase_invoices_export.csv"))) {
            writer.write("Invoice ID,Supplier,Issue Date,Total Amount\n");

            for (PurchaseInvoice invoice : currentInvoices) {
                writer.write(String.format("%d,%s,%s,%.2f\n",
                        invoice.getId(),
                        invoice.getSupplierName(),
                        invoice.getCreatedAt().toString(),
                        invoice.getTotalAmount()
                ));
            }

            showAlert("Export Successful", "Purchase invoices exported to 'purchase_invoices_export.csv'");

        } catch (IOException e) {
            showAlert("Export Failed", "Could not save CSV file: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}