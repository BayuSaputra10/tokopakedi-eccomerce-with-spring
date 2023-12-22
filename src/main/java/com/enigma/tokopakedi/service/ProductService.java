package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    Page<Product> paging(Pageable pageable);
    List<Product> getAll();

    Product getById(Product product);

    Product create(Product product);

    Product update(Product product);

    void deleteById(String id);
}
