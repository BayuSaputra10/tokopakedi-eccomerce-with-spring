package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }



    @Override
    public Page<Customer> paging(org.springframework.data.domain.Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Error");
        customerRepository.deleteById(id);

    }

    @Override
    public Customer findById(Customer customer) {
        Optional<Customer> byIdResult = customerRepository.findById(customer.getId());
        return byIdResult.orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> byIdFind = customerRepository.findById(customer.getId());
        if (byIdFind.isEmpty())throw new RuntimeException("Can not find Data");
        return customerRepository.save(customer);
    }
}
