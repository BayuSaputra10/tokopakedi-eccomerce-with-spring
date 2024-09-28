package com.enigma.tokopakedi.controller;


import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CustomerService customerService;
    private final ProductService productService;


    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request){
        OrderResponse transaction = orderService.createTransaction(request);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("succesfully Created")
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllTransaction(@RequestParam(required = false,defaultValue = "1") Integer page,
                                               @RequestParam(required = false,defaultValue = "10") Integer size){
        SearchOrderRequest orderRequest = SearchOrderRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Order> allPage = orderService.getAll(orderRequest);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(orderRequest.getPage())
                .size(orderRequest.getSize())
                .totalPages(allPage.getTotalPages())
                .totalELement(allPage.getTotalElements())
                .build();

        WebResponse<List<Order>> orderWebResponse = WebResponse.<List<Order>>builder()
        .status(HttpStatus.OK.getReasonPhrase())
        .message("Success get All data")
        .data(allPage.getContent())
        .paging(pagingResponse)
        .build();


        return ResponseEntity.status(HttpStatus.OK).body(orderWebResponse);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Order order = orderService.getById(id);
        WebResponse<Order> orderWebResponse = WebResponse.<Order>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Succes Get by Id")
                .data(order)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(orderWebResponse);
    }
}
