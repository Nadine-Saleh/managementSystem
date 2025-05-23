package com.project.managementsystem.erp.facade;


import com.project.managementsystem.erp.services.*;


import com.project.managementsystem.erp.models.*;


import java.time.LocalDate;
import java.util.List;
import com.project.managementsystem.erp.services.PurchaseInvoiceService;
import com.project.managementsystem.erp.dao.PurchaseInvoiceDAOImpl;
import com.project.managementsystem.erp.dao.LineItemDAOImpl;
import com.project.managementsystem.erp.dao.InventoryDAOImpl;
/**
 * Facade class that provides a simplified interface to the ERP system.
 * Combines product, inventory, customer, and invoice operations into one class.
 */
public class ERPSystemFacade {

    // Services
    private final AddSupplierService addSupplierService;
  private final ProductService productService;
  private final PaymentService paymentService;
    private final AddCustomerService addcustomerService;
    private final PurchaseInvoiceService purchaseInvoiceService;
//    private final DefaultInvoiceService invoiceService;

    /**
     * Constructor initializes all services with their DAO implementations.
     */
    public ERPSystemFacade() {
        this.purchaseInvoiceService = new PurchaseInvoiceService(
                new PurchaseInvoiceDAOImpl(),
                new LineItemDAOImpl()
        );
        this.productService = new ProductService();
        this.paymentService = new PaymentService();
//        this.inventoryService = new DefaultInventoryService(new InventoryDAOImpl());
        this.addcustomerService = new AddCustomerService();
//        this.invoiceService = new DefaultInvoiceService(new InvoiceDAOImpl());
        this.addSupplierService = new AddSupplierService();
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
    // === Purchase invoices METHODS ===
    public void createPurchaseInvoice(Supplier supplier, int supplierId, LocalDate date, List<LineItem> items) throws Exception {
        purchaseInvoiceService.createPurchaseInvoice(supplier, supplierId, date, items);

    }
    // === PAYMENT METHODS ===

    public void addPayment(Payment payment) {
        paymentService.addPayment(payment);
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

    public void addCustomer(Customer customer) {
        addcustomerService.addCustomer(customer);
    }
    

//    public Customer getCustomerById(int id) {
//        return addcustomerService.getById(id);
//    }
//
   public List<Customer> getAllCustomers() {
       return addcustomerService.getAllCustomers();
   }
    public void addSupplier(Supplier supplier) {addSupplierService.addSupplier(supplier);}
    public List<Supplier> getAllSuppliers() {return addSupplierService.getAllSuppliers();}
//
//    public void updateCustomer(Customer customer) {
//        addcustomerService.update(customer);
//    }
//
//    public void deleteCustomer(int id) {
//        addcustomerService.delete(id);
//    }

    // === INVOICE METHODS ===

//    public void createInvoice(Invoice invoice) {
////        invoiceService.save(invoice);
//    }
//

//  public Invoice getInvoiceById(int id) {
//        // invoiceService的getById方法，根据id获取发票
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