package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


//    @GetMapping(path = "/customers")
//    public Page<Customer> pageable(@RequestParam(name = "page") Integer page,@RequestParam(name = "size") Integer size){
//        PageRequest pageRequest = PageRequest.of(page,size);
//        return customerService.paging(pageRequest);
//    }


    @GetMapping(path = "customer")
    public ResponseEntity<WebResponse<Customer>> getById(@RequestParam Customer id){
        Customer byId = customerService.findById(id.getId());

        WebResponse<Customer> webResponse = WebResponse.<Customer>builder()
                .message(byId.getName() + "Ditemukan")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(byId)
                .build();

        return ResponseEntity.ok(webResponse);
    }

//    @PostMapping(path = "/customers")
//    public ResponseEntity<WebResponse<Customer>> newCustomer(@RequestBody Customer customer) {
//        Customer customer1 = customerService.create(customer);
//        WebResponse<Customer> customerWebResponse = WebResponse.<Customer>builder()
//                .message(customer.getName() + " Succes To Add Data")
//                .status(HttpStatus.CREATED.getReasonPhrase())
//                .data(customer1)
//                .build();
//
//        return ResponseEntity.ok(customerWebResponse);
//    }

    @DeleteMapping(path = "/customers")
    public ResponseEntity<WebResponse<Customer>> deleteCustomer(@RequestParam String id) {
        Customer customerDelete = customerService.deleteById(id);
        WebResponse<Customer> webResponse = WebResponse.<Customer>builder()
                .message(id + " Succes To Delete")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customerDelete)
                .build();

        return ResponseEntity.ok(webResponse);
    }

    @GetMapping(path = "/customerst")
    public ResponseEntity<WebResponse<Customer>> customerfindById(@RequestParam(name = "id") Customer customer) {
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .message("Succes Get Customer By Id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<WebResponse<List<Customer>>> customerList(@RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(required = false) String name,
                                                                    @RequestParam(required = false) String phoneNumber){

        SearchCustomerRequest customerRequest = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();

        Page<Customer> customerPage = customerService.getAll(customerRequest);
        PagingResponse pagingResponse = PagingResponse.<Customer>builder()
                .page(customerRequest.getPage())
                .size(customerRequest.getSize())
                .totalPages(customerPage.getTotalPages())
                .totalELement(customerPage.getTotalElements())
                .build();

        WebResponse<List<Customer>> response = WebResponse.<List<Customer>>builder()
                .message("Success Get All Customers")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(customerPage.getContent())
                .build();

        return ResponseEntity.ok(response);
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
    public ResponseEntity<WebResponse<Customer>> updateCustomer(@RequestBody Customer customer){


        WebResponse<Customer> customerWebResponse = WebResponse.<Customer>builder()
                .message(customer.getName() +" Succes To Update ")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();

        return ResponseEntity.ok(customerWebResponse);
    }
}