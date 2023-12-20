package com.enigma.tokopakedi.controller;


import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/products")
    public List<Product> getProducts(){
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }
    @GetMapping(path = "/products/{id}")
    public Product getProductsById(@PathVariable Product id){
        Optional<Product> byId = productRepository.findById(id.getId());
        return byId.orElse(null);
    }


    @PostMapping(path = "/products")
    public Product addProducts(@RequestBody Product product){
        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @PutMapping(path = "/products")
    public Product updateProduct(@RequestBody Product product){
        Product updateProduct = productRepository.save(product);
        return updateProduct;
    }

    @DeleteMapping(path = "/products/{id}")
    public void deleteById(@PathVariable String id){
        productRepository.deleteById(id);
    }
}
