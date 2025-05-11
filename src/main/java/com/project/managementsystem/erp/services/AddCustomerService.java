package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.CustomerDAO;
import com.project.managementsystem.erp.dao.CustomerDAOImpl;
import com.project.managementsystem.erp.models.Customer;

import java.util.regex.Pattern;

public class AddCustomerService {

    private final CustomerDAO customerDAO;

    public AddCustomerService() {
        this.customerDAO = new CustomerDAOImpl(); // Use interface reference
    }

    /**
     * Adds a new customer after validating inputs.
     *
     * @param customer The customer object with name, email, balance, phone
     * @return true if customer was added successfully, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        String name = customer.getName();
        String email = customer.getEmail();
        String balanceStr = customer.getBalance(); // Assuming getBalance() returns String
        String phone = customer.getPhone();

        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (email != null && !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        double balanceValue = 0.0;
        try {
            if (balanceStr != null && !balanceStr.trim().isEmpty()) {
                balanceValue = Double.parseDouble(balanceStr);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid balance format.");
        }

        // Set validated balance as double or string depending on model
        customer.setBalance(String.valueOf(balanceValue)); // Adjust based on your model

        // Save to DB
        try {
            customerDAO.addCustomer(customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates email using simple regex pattern.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}