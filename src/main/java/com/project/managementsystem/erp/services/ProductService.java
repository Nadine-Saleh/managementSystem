package com.project.managementsystem.erp.services;

import com.project.managementsystem.erp.dao.ProductDAO;
import com.project.managementsystem.erp.dao.ProductDAOImpl;
import com.project.managementsystem.erp.models.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAOImpl();
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public Product getProduct(int id) {
        return productDAO.getById(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void removeProduct(int id) {
        productDAO.delete(id);
    }
}