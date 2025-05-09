package com.project.managementsystem.erp.dao;

import com.project.managementsystem.erp.models.Inventory;

import java.util.List;

/**
 * Data Access Object (DAO) for Inventory entity.
 * Defines standard CRUD operations for inventory records.
 */
public interface InventoryDAO {

    /**
     * Adds a new inventory record to the database.
     *
     * @param inventory The Inventory object to save
     */
    void addInventory(Inventory inventory);

    /**
     * Updates an existing inventory record.
     *
     * @param inventory The updated Inventory object
     */
    void updateInventory(Inventory inventory);

    /**
     * Deletes an inventory record by its ID.
     *
     * @param id The ID of the inventory record to delete
     */
    void deleteInventory(int id);

    /**
     * Retrieves an inventory record by its ID.
     *
     * @param id The ID of the inventory record
     * @return Inventory object if found, null otherwise
     */
    Inventory getInventoryById(int id);

    /**
     * Retrieves all inventory records from the database.
     *
     * @return List of Inventory objects
     */
    List<Inventory> getAllInventory();

    /**
     * Retrieves all inventory records for a specific product.
     *
     * @param productId The ID of the product
     * @return List of Inventory entries for that product
     */
    List<Inventory> getInventoryByProductId(int productId);

    /**
     * Adjusts the stock level of a product.
     * Useful when recording sales, purchases, or manual adjustments.
     *
     * @param productId       The ID of the product
     * @param change          The amount to adjust (+ for IN, - for OUT)
     * @param changeType      Type of change ("IN", "OUT", "ADJUSTMENT")
     * @param referenceType   Reference type (e.g., "sales_order")
     * @param referenceId     Reference ID (e.g., order ID)
     */
//    static void adjustStock(int productId, int change, String changeType, String referenceType, int referenceId);
}