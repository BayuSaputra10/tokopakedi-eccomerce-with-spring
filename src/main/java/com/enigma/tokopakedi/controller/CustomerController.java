package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {


    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }


    @GetMapping(path = "/customer")
    public Page<Customer> pageable(@RequestParam(name = "page") Integer page,@RequestParam(name = "size") Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        return customerService.paging(pageRequest);
    }



    @PostMapping(path = "/customers")
    public Customer newCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @DeleteMapping(path = "/customers/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Customer customer) {
    customerService.deleteById(customer.getId());
    }

    @GetMapping(path = "/customers/{id}")
    public Customer customerfindById(@PathVariable(name = "id") Customer customer) {
        return customerService.findById(customer);
    }

    @GetMapping(path = "/customers")
    public List<Customer> customerList(){   
       return customerService.findAll();
    }

    @GetMapping(path =  "/customer/search")
    public List<Customer> customerListBy(@RequestParam(name = "name",required = false) String name,
                                         @RequestParam(name = "phoneNumber",required = false) String phoneNumber){
       if(phoneNumber != null) {
           return customerRepository.findAllByphoneNumberContaining(phoneNumber);
       } else if (name != null) {
           return customerRepository.findAllByNameContaining(name);
       }else {
           return null;
       }
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }
}