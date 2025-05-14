package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.factory.FxmlLoaderUtil;
import com.project.managementsystem.erp.factory.InvoiceViewFactory;
import com.project.managementsystem.erp.factory.InvoiceViewFactoryProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionsMenuController {

    @FXML
    private Button newInvoiceBtn;

    @FXML
    private Button showAllInvoicesBtn;

    @FXML
    private Button showInvoiceBtn;

    private String currentInvoiceType;

    public void initialize(String type) {
        this.currentInvoiceType = type;
    }

    @FXML
    private void handleNewInvoice() {
        InvoiceViewFactory factory = InvoiceViewFactoryProvider.getFactory(currentInvoiceType);
        FxmlLoaderUtil.openInvoiceWindow(OptionsMenuController.class, factory.loadNewInvoicePath(), "New invoice");
    }

    @FXML
    private void handleShowInvoice() {
        InvoiceViewFactory factory = InvoiceViewFactoryProvider.getFactory(currentInvoiceType);
        FxmlLoaderUtil.openInvoiceWindow(OptionsMenuController.class, factory.loadShowInvoicePath(), "Open an invoice");
    }

    @FXML
    private void handleShowAllInvoices() {
        InvoiceViewFactory factory = InvoiceViewFactoryProvider.getFactory(currentInvoiceType);
        FxmlLoaderUtil.openInvoiceWindow(OptionsMenuController.class, factory.loadShowAllInvoicesPath(), "Show all invoices");
    }
}
