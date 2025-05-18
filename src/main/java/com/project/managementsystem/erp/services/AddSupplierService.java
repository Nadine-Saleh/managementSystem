package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.SupplierDAO;
import com.project.managementsystem.erp.dao.SupplierDAOImpl;
import com.project.managementsystem.erp.models.Supplier;

import java.util.List;
import java.util.regex.Pattern;

public class AddSupplierService {

    private final SupplierDAO supplierDAO;

    public AddSupplierService() {
        this.supplierDAO = new SupplierDAOImpl();
    }

    public boolean addSupplier(Supplier supplier) {
        String name = supplier.getName();
        String email = supplier.getEmail();
        String phone = supplier.getPhone();

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (email != null && !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        try {
            supplierDAO.addSupplier(supplier);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}