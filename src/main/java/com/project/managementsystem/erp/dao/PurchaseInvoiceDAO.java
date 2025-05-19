package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.PurchaseInvoice;

import java.util.List;

public interface PurchaseInvoiceDAO {

    int save(PurchaseInvoice invoice);
    void update(PurchaseInvoice invoice);
    void delete(int id);
    PurchaseInvoice getById(int id);
    List<PurchaseInvoice> getAll();
}