package com.ngominhthuan.product_and_order_api.service;

import com.ngominhthuan.product_and_order_api.model.Product;
import com.ngominhthuan.product_and_order_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product details by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Create or Update product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}