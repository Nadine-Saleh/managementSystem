package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Customer;

import java.util.List;

/**
 * Data Access Object (DAO) for Customer entity.
 * Defines standard CRUD operations for customer records.
 */
public interface CustomerDAO {

    /**
     * Adds a new customer to the database.
     *
     * @param customer The Customer object to save
     */
    void addCustomer(Customer customer);

    /**
     * Updates an existing customer in the database.
     *
     * @param customer The updated Customer object
     */
    void updateCustomer(Customer customer);

    /**
     * Deletes a customer by ID.
     *
     * @param id The ID of the customer to delete
     */
    void deleteCustomer(int id);

    /**
     * Retrieves a customer by ID.
     *
     * @param id The ID of the customer
     * @return Customer object if found, null otherwise
     */
    Customer getCustomerById(int id);

    /**
     * Retrieves all customers from the database.
     *
     * @return List of Customer objects
     */
    List<Customer> getAllCustomers();

    /**
     * Searches customers by name or partial name.
     *
     * @param keyword Name or part of name to search
     * @return List of matching Customer objects
     */
    List<Customer> searchCustomers(String keyword);
}