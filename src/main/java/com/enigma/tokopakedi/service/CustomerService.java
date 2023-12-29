package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.SearchProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {


//    Page<Customer> paging(Pageable pageable);

    Customer create(Customer customer);

    Customer deleteById(String id);

    Customer findById(String customer);

    Page<Customer> getAll(SearchCustomerRequest request);

    Customer updateCustomer(Customer customer);
}
