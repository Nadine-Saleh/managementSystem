package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Supplier;

import java.util.List;

public interface SupplierDAO {
    void addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void deleteSupplier(int id);
    Supplier getSupplierById(int id);
    List<Supplier> getAllSuppliers();
    List<Supplier> searchSuppliers(String keyword);
}