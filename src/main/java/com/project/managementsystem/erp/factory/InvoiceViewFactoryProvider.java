package com.project.managementsystem.erp.factory;

public class InvoiceViewFactoryProvider {
    public static InvoiceViewFactory getFactory(String invoiceType) {
        if ("sales".equalsIgnoreCase(invoiceType)) {
            return new SalesInvoiceViewFactory();
        } else if ("purchase".equalsIgnoreCase(invoiceType)) {
            return new PurchaseInvoiceViewFactory();
        }
        throw new IllegalArgumentException("unknown type of invoice: " + invoiceType);
    }
}
