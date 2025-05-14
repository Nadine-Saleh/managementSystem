package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.InvoiceDAO;
import com.project.managementsystem.erp.dao.InvoiceDAOImpl;
import com.project.managementsystem.erp.models.Invoice;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class SalesInvoicesController {

    @FXML private TextField searchByCustomerName;
    @FXML private Button searchBtn;
    @FXML private Button DwonloadBtn;
    @FXML private TableView<Invoice> invoicesTable;
    @FXML private TableColumn<Invoice, Integer> invoiceid;
    @FXML private TableColumn<Invoice, String> invoiceCustomer;
    @FXML private TableColumn<Invoice, String> invoiceDate;
    @FXML private TableColumn<Invoice, Double> invoiceTotalAmount;

    private final InvoiceDAO invoiceDAO = new InvoiceDAOImpl();

    /**
     * Called automatically after FXML is loaded.
     * Initialize table columns and load all sales invoices.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadAllSalesInvoices();
    }

    private void setupTableColumns() {
        invoiceid.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject()); // match field name in Invoice
        invoiceCustomer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        invoiceDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
        invoiceTotalAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalAmount()).asObject());
    }

    private void loadAllSalesInvoices() {
        try {
            List<Invoice> salesInvoices = invoiceDAO.getByType("SALE");
            // System.out.println(salesInvoices);
            ObservableList<Invoice> observableInvoices = FXCollections.observableArrayList(salesInvoices);
            invoicesTable.setItems(observableInvoices);
        } catch (Exception e) {
                    e.printStackTrace(); // 👈 Add this line

            showAlert("Error", "Failed to load invoices.");
        }
    }

    @FXML
    public void onSearchClick(ActionEvent event) {
        String keyword = searchByCustomerName.getText().trim();

        if (keyword.isEmpty()) {
            loadAllSalesInvoices();
            return;
        }

        try {
            List<Invoice> results = invoiceDAO.searchByCustomerName(keyword);
            System.err.println(results);
            invoicesTable.setItems(FXCollections.observableArrayList(results));
        } catch (Exception e) {
            showAlert("Search Error", "Could not search invoices: " + e.getMessage());
        }
    }

    @FXML
    public void downloadCSV(ActionEvent event) {
        List<Invoice> currentInvoices = invoicesTable.getItems();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sales_invoices_export.csv"))) {
            writer.write("Invoice Number,Customer,Issue Date,Total Amount\n");

            for (Invoice invoice : currentInvoices) {
                writer.write(String.format("%s,%s,%s,%.2f\n",
                        // invoice.getInvoiceNumber(),
                        invoice.getCustomerName(),
                        invoice.getCreatedAt(),
                        invoice.getCustomerId(),
                        invoice.getTotalAmount()
                ));
            }

            showAlert("Export Successful", "Invoices exported to 'sales_invoices_export.csv'");

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