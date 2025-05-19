package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Product;

import java.util.List;

/**
 * Data Access Object (DAO) interface for Product entities.
 * Defines the contract for classes that interact with the Product table in the database.
 */
public interface ProductDAO {

    /**
     * Saves a new product to the database.
     *
     * @param product The product to be saved.
     */
    void save(Product product);
    List<Product> getAllProducts();

    /**
     * Retrieves a product by its unique ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The Product object if found, or null if not found.
     */
    Product getById(int id);

    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products.
     */
    List<Product> getAll();

    /**
     * Updates an existing product in the database.
     *
     * @param product The updated product data.
     */
    void update(Product product);

    /**
     * Deletes a product from the database based on its ID.
     *
     * @param id The ID of the product to delete.
     */
    void delete(int id);
}