package com.project.managementsystem.erp.factory;

public class SalesInvoiceViewFactory implements InvoiceViewFactory {
    @Override
    public String loadNewInvoicePath() {
        return "fxml/addSalesInvoice.fxml";
    }

    @Override
    public String loadShowInvoicePath() {
        return "fxml/openSalesInvoice.fxml";
    }

    @Override
    public String loadShowAllInvoicesPath() {
        return "fxml/SalesInvoices.fxml";
    }
}
