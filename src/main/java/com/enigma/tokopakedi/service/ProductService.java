package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProductService {

//    Page<Product> paging(SearchProductRequest request);
    Page<Product> getAll(SearchProductRequest request);
    List<Product> setBulk(List<Product> addList);
    Product getById(String product);

    Product create(Product product);

    Product update(Product product);

    ResponseEntity<?> deleteById(String id);
}
