package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
//    @Override
//    public Page<Product> paging(SearchProductRequest request) {
//       if (request.getPage() <= 0) request.setPage(1);
//       Pageable pageable = PageRequest.of(
//                (request.getPage() - 1), request.getSize());
//        Specification<Product> specification = getProductSpecification(request);
//        return productRepository.findAll(specification,pageable);
//    }

    @Override
    public Page<Product> getAll(SearchProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(
                (request.getPage() - 1), request.getSize());
        Specification<Product> specification = getProductSpecification(request);
        return productRepository.findAll(specification,pageable);
    }

    @Override
    public List<Product> setBulk(List<Product> addList) {
        return productRepository.saveAll(addList);
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
        if (byIdFind.isEmpty() )throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return productRepository.save(product);
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        Optional<Product> byIdFind = productRepository.findById(id);
        if (byIdFind.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        productRepository.deleteById(id);
        return null;
    }

    private Specification<Product> getProductSpecification(SearchProductRequest request) {
        Specification<Product> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null){
                Predicate addNamePredicate =  criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%");
                predicates.add(addNamePredicate);
            }
            if (request.getMinPrice() != null){
                Predicate minPricePredicate =  criteriaBuilder.greaterThanOrEqualTo(root.get("price"),+ request.getMinPrice());
                predicates.add(minPricePredicate);
            }
            if (request.getMaxPrice() != null){
                Predicate maxPricePredicate =  criteriaBuilder.lessThanOrEqualTo(root.get("price"),+ request.getMaxPrice());
                predicates.add(maxPricePredicate);
            }

            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
        return specification;
    }

}
