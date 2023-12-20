package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {


    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(path = "/customers")
    public Customer newCustomer(@RequestBody Customer customer) {
        Customer saveCustomer = customerRepository.save(customer);
        return saveCustomer;
    }

    @DeleteMapping(path = "/customers")
    public void deleteCustomer(@RequestBody Customer customer) {
        customerRepository.deleteById(customer.getId());

    }

    @PostMapping(path = "/customers/id")
    public Customer customerfindById(@RequestBody Customer customer) {
        Optional<Customer> byId = customerRepository.findById(customer.getId());
        return byId.orElse(null);
    }

    @GetMapping(path = "/customers")
    public List<Customer> customerList(){
        List<Customer> resultCustomer = customerRepository.findAll();
        return resultCustomer;
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        Customer updateCustomer = customerRepository.save(customer);
        return updateCustomer;
    }
}