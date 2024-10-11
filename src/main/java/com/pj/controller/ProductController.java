package com.pj.controller;

import com.pj.dto.ProductDTO;
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

    /**
     * 获取所有产品
     * 现在使用 POST 方法
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts(@RequestBody(required = false) ProductDTO request) {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * 根据产品代码获取产品
     * 现在使用 POST 方法
     */
    @PostMapping("/getByCode")
    public ResponseEntity<Product> getProductByCode(@RequestBody ProductDTO request) {
        String productCode = request.getProductCode();
        Optional<Product> product = productRepository.findByProductCode(productCode);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 添加新产品
     */
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * 更新产品信息
     */
    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            return ResponseEntity.notFound().build();
        }
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }


    /**
     * 删除产品
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}