package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;



    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }


    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll( pageable);
    }



//    @Override
//    public Page<Customer> paging(org.springframework.data.domain.Pageable pageable) {
//        return customerRepository.findAll(pageable);
//    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteById(String id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Error");
        customerRepository.deleteById(id);
        return byId.get();
    }

    @Override
    public Customer findById(String customer) {
        Optional<Customer> byIdResult = customerRepository.findById(customer);
        return byIdResult.orElse(null);
    }

    @Override
    public Page<Customer> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        org.springframework.data.domain.Pageable pageable = PageRequest.of(
                (request.getPage() - 1), request.getSize());
        Specification<Customer> specification = getCustomerSpecification(request);

        return customerRepository.findAll(specification,pageable);
    }



    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> byIdFind = customerRepository.findById(customer.getId());
        if (byIdFind.isEmpty())throw new RuntimeException("Can not find Data");

        UserCredential userCredential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCredential credential = byIdFind.get().getUserCredential();

        if (!userCredential.getId().equals(credential.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Forbidden");

        customer.setUserCredential(credential);

        return customerRepository.save(customer);
    }

    private static Specification<Customer> getCustomerSpecification(SearchCustomerRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null){
                Predicate addNamePredicate =  criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%");
                predicates.add(addNamePredicate);
            } if (request.getPhoneNumber() != null){
                Predicate addNamePredicate =  criteriaBuilder.equal(root.get("phoneNumber"), request.getPhoneNumber());
                predicates.add(addNamePredicate);
            }
            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
    }
}
