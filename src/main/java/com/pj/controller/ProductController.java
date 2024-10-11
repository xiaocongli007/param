package com.pj.controller;

import com.pj.model.Product;
import com.pj.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 获取所有产品
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // 根据产品类型获取单个产品
    @GetMapping("/{productType}")
    public ResponseEntity<?> getProductByType(@PathVariable String productType) {
        Optional<Product> product = productRepository.findByProductType(productType);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(404).body("Product not found");
        }
    }
}