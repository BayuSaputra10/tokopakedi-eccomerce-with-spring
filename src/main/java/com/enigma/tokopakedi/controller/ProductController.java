package com.enigma.tokopakedi.controller;


import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository, ProductService productService, ProductRepository productRepository1) {
        this.productService = productService;
        this.productRepository = productRepository1;
    }
//    @GetMapping(path = "/products")
//    public ResponseEntity<WebResponse<List<Product>>> getProducts(){
//        List<Product> allProduct = productService.getAll();
//
//        WebResponse<List<Product>> webResponse = WebResponse.<List<Product>>builder()
//                .message("Succesfully get all product")
//                .status(HttpStatus.OK.getReasonPhrase())
//                .data(allProduct)
//                .build();
//        return ResponseEntity.ok(webResponse);
//
//

    @GetMapping(path = "/product")
    public ResponseEntity<WebResponse<Product>> getProductsById(@RequestParam Product id){
        Product byId = productService.getById(id);

        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("Succesfully get product by Id" )
                .status(HttpStatus.OK.getReasonPhrase())
                .data(byId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/products")
    public ResponseEntity<Product> addProducts(@RequestBody Product product){
        Product newCreatedProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCreatedProduct);
    }

    @PutMapping(path = "/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product updateProduct = productService.update(product);
        return ResponseEntity.ok(updateProduct);
    }

    @PostMapping(path = "/product/bulk")
    public ResponseEntity<List<Product>> AddBulkProduct(@RequestBody List<Product> productList){
        List<Product> newProductList = productService.setBulk(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductList);
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
       return productService.deleteById(id);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<WebResponse<List<Product>>> productPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(name = "size",defaultValue = "10") Integer size,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer minPrice,
                                                   @RequestParam(required = false) Integer maxPrice){

        SearchProductRequest request = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .minPrice(minPrice).maxPrice(maxPrice)
                .build();

        Page<Product> paging = productService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.<Product>builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(paging.getTotalPages())
                .totalELement(paging.getTotalElements())
                .build();

        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(paging.getContent())
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/product/search")
    public ResponseEntity<List<Product>> productList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice){

        if (name != null) {
            List<Product> allByNameLike = productRepository.findAllByNameLike(name);
            return ResponseEntity.ok(allByNameLike);
        } else if (minPrice != null) {
            List<Product> byPriceLessThan = productRepository.findByPriceLessThan(minPrice);
            return ResponseEntity.ok(byPriceLessThan);
        } else if (maxPrice != null){
            List<Product> byPriceGreaterThan = productRepository.findByPriceGreaterThan(maxPrice);
            return ResponseEntity.ok(byPriceGreaterThan);
        }else {
            List<Product> byPriceGreaterThanAndPriceLessThan = productRepository.findByPriceGreaterThanAndPriceLessThan(minPrice, maxPrice);
            return ResponseEntity.ok(byPriceGreaterThanAndPriceLessThan);
        }
    }
}
