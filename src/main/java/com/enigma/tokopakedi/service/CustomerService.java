package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {


    Page<Customer> paging(Pageable pageable);

    Customer create(Customer customer);

    void deleteById(String id);

    Customer findById(Customer customer);

    List<Customer> findAll();

    Customer updateCustomer(Customer customer);
}
