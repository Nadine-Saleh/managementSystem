package com.project.managementsystem.erp.factory;

public class PurchaseInvoiceViewFactory implements InvoiceViewFactory {
    @Override
    public String loadNewInvoicePath() {
        return "fxml/newPurchaseInvoice.fxml";
    }

    @Override
    public String loadShowInvoicePath() {
        return "fxml/PurchaseInvoice.fxml";
    }

    @Override
    public String loadShowAllInvoicesPath() {
        return "fxml/PurchaseInvoices.fxml";
    }
}
