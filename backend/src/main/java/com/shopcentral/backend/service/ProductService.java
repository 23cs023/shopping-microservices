package com.shopcentral.backend.service;

import com.shopcentral.backend.model.Product;
import com.shopcentral.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

  
    public Product addProduct(Product product) {

        // If frontend does not send stock,
        // automatically set stock to 10
        if (product.getStock() == null || product.getStock() <= 0) {
            product.setStock(10);
        }

        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete Product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}