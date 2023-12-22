package com.enigma.tokopakedi.controller;


import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository, ProductService productService, ProductRepository productRepository1) {
        this.productService = productService;
        this.productRepository = productRepository1;
    }

    @GetMapping(path = "/products")
    public List<Product> getProducts(){
        return productService.getAll();
    }
    @GetMapping(path = "/products/{id}")
    public Product getProductsById(@PathVariable Product id){
        return productService.getById(id);
    }


    @PostMapping(path = "/products")
    public Product addProducts(@RequestBody Product product){
       return productService.create(product);
    }

    @PutMapping(path = "/products")
    public Product updateProduct(@RequestBody Product product){
       return productService.update(product);
    }

    @DeleteMapping(path = "/products/{id}")
    public void deleteById(@PathVariable String id){
       productService.deleteById(id);
    }

    @GetMapping(path = "/product")
    public Page<Product> productPage(@RequestParam(name = "page") Integer page,@RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page,size);
        return productService.paging(pageable);
    }

//    @GetMapping(path = "/product/search")
//public List<Product> productList(@RequestParam(name = "minPrice") Integer minPrice){
//    Specification<Product> productSpecification = (root, query, criteriaBuilder) -> {
//        return query.where((criteriaBuilder.equal(root.get("minPrice"), minPrice)
//        )).getRestriction();
//};
//        return productService.findAll(productSpecification);
//    }


//    @GetMapping(path = "/product/search")
//    public List<Product> productList(@RequestParam(name = "name") String name){
//        return productRepository.findAllByNameLike(name);
//
//    }
//    @GetMapping(path = "/product/search")
//    public List<Product> productList2(@RequestParam(name = "minPrice") Integer minPrice){
//        return productRepository.findByPriceLessThan(minPrice);
//    }

    @GetMapping(path = "/product/search")
    public List<Product> productList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice){

        if (name != null) {
            return productRepository.findAllByNameLike(name);
        } else if (minPrice != null) {
            return productRepository.findByPriceLessThan(minPrice);
        } else if (maxPrice != null){
            return productRepository.findByPriceGreaterThan(maxPrice);
        }else {
            return productRepository.findByPriceGreaterThanAndPriceLessThan(minPrice,maxPrice);
        }
    }
}
