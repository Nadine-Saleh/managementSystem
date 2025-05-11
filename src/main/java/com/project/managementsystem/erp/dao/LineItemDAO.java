package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.LineItem;

import java.util.List;

public interface LineItemDAO {

    void addLineItem(LineItem item);

    void updateLineItem(LineItem item);

    void deleteLineItem(int id);

    LineItem getLineItemById(int id);

    List<LineItem> getLineItemsByInvoiceId(int invoiceId , String type);

    List<LineItem> getAllLineItems();
}