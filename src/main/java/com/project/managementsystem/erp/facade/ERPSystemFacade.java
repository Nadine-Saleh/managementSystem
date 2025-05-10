package com.project.managementsystem.erp.facade;


import com.project.managementsystem.erp.services.*;


import com.project.managementsystem.erp.models.*;


import java.util.List;

/**
 * Facade class that provides a simplified interface to the ERP system.
 * Combines product, inventory, customer, and invoice operations into one class.
 */
public class ERPSystemFacade {

    // Services
  private final ProductService productService;
//    private final DefaultInventoryService inventoryService;
//    private final DefaultCustomerService customerService;
//    private final DefaultInvoiceService invoiceService;

    /**
     * Constructor initializes all services with their DAO implementations.
     */
    public ERPSystemFacade() {
        this.productService = new ProductService();
//        this.inventoryService = new DefaultInventoryService(new InventoryDAOImpl());
//        this.customerService = new DefaultCustomerService(new CustomerDAOImpl());
//        this.invoiceService = new DefaultInvoiceService(new InvoiceDAOImpl());
    }

    // === PRODUCT METHODS ===

    public void addProduct(Product product) {
        productService.addProduct(product);
    }

    public Product getProductById(int id) {
        return productService.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }

    public void deleteProduct(int id) {
//        productService.delete(id);
    }

    // === INVENTORY METHODS ===

//    public void updateInventory(Inventory inventory) {
////        inventoryService.update(inventory);
//    }
//
//    public Inventory getInventoryByProductId(int productId) {
////        return inventoryService.getByProductId(productId);
//    }

    // === CUSTOMER METHODS ===

//    public void addCustomer(Customer customer) {
////        customerService.save(customer);
//    }
//
//    public Customer getCustomerById(int id) {
////        return customerService.getById(id);
//    }
//
//    public List<Customer> getAllCustomers() {
////        return customerService.getAll();
//    }
//
//    public void updateCustomer(Customer customer) {
////        customerService.update(customer);
//    }
//
//    public void deleteCustomer(int id) {
////        customerService.delete(id);
//    }

    // === INVOICE METHODS ===

//    public void createInvoice(Invoice invoice) {
////        invoiceService.save(invoice);
//    }
//
//// 根据id获取发票
//  public Invoice getInvoiceById(int id) {
//        // 调用invoiceService的getById方法，根据id获取发票
//        return invoiceService.getById(id);
//    }
//
//    public List<Invoice> getAllInvoices() {
//        return InvoiceService.getAll();
//    }
//
//    public void updateInvoice(Invoice invoice) {
//        InvoiceService.update(invoice);
//    }
//
//    public void deleteInvoice(int id) {
//        InvoiceService.delete(id);
//    }
}