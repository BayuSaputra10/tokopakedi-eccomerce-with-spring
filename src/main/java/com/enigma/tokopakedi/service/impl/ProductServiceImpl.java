package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }







    @Override
    public Page<Product> paging(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Product product) {
        Optional<Product> byId = productRepository.findById(product.getId());
        return byId.orElse(null);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Optional<Product> byIdFind = productRepository.findById(product.getId());
        if (byIdFind.isEmpty() )throw new RuntimeException("Data Not Found");
        return productRepository.save(product);
    }

    @Override
    public void deleteById(String id) {
        Optional<Product> byIdFind = productRepository.findById(id);
        if (byIdFind.isEmpty())throw new RuntimeException("Data Not Found");
        productRepository.deleteById(id);
    }


}
