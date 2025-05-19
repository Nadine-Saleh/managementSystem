package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.PurchaseInvoiceDAO;
import com.project.managementsystem.erp.dao.LineItemDAO;
import com.project.managementsystem.erp.models.PurchaseInvoice;
import com.project.managementsystem.erp.models.LineItem;
import com.project.managementsystem.erp.models.Supplier;

import java.time.LocalDate;
import java.util.List;

public class PurchaseInvoiceService {

    private final PurchaseInvoiceDAO invoiceDAO;
    private final LineItemDAO lineItemDAO;

    public PurchaseInvoiceService(PurchaseInvoiceDAO invoiceDAO, LineItemDAO lineItemDAO) {
        this.invoiceDAO = invoiceDAO;
        this.lineItemDAO = lineItemDAO;
    }

    public void createPurchaseInvoice(Supplier supplier, int supplierId, LocalDate date, List<LineItem> items) throws Exception {
        PurchaseInvoice invoice = new PurchaseInvoice();
        invoice.setSupplierName(supplier.getName());
        invoice.setSupplierId(supplierId);
        invoice.setCreatedAt(date);
        invoice.setItems(items);
        invoice.setType("PURCHASE");
        invoice.setTotalAmount(items.stream().mapToDouble(LineItem::getTotal).sum());

        int invoiceId = invoiceDAO.save(invoice);

        for (LineItem item : items) {
            item.setInvoiceId(invoiceId);
            item.setType("PURCHASE");
            lineItemDAO.addLineItem(item);
        }
    }
}