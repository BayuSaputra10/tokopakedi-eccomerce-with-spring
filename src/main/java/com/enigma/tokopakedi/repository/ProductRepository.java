package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByNameLike(String name);
    List<Product> findByPriceGreaterThan(Integer minPrice);
    List<Product> findByPriceGreaterThanAndPriceLessThan(Integer minPrice, Integer maxPrice);

    List<Product> findByPriceLessThan(Integer maxPrice);


}
