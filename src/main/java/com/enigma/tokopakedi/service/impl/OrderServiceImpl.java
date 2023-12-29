package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.repository.OrderRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderDetailService;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerService customerService;

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private final OrderDetailService orderDetailService;

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Order createTransaction(OrderRequest request) {
//
//        Customer customer = customerService.findById(request.getCustomerId());
//
//        Order order = Order.builder()
//                .customer(customer)
//                .date(new Date())
//                .build();
//        orderRepository.saveAndFlush(order);
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
//            Product product = productService.getById(orderDetailRequest.getProductId());
//
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .order(order)
//                    .product(product)
//                    .productPrice(product.getPrice())
//                    .quantity(orderDetailRequest.getQuantity())
//                    .build();
//
//            if (product.getStock() - orderDetailRequest.getQuantity() < 0){
//                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity Exceeds");
//            }
//
//                product.setStock(product.getStock() - orderDetail.getQuantity());
//
//                orderDetails.add(orderDetailService.createOrUpdate(orderDetail));
//        }
//
//        order.setOrderDetails(orderDetails);
//        return order;
//    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createTransaction(OrderRequest request) {

        Customer customer = customerService.findById(request.getCustomerId());

        Order order = Order.builder()
                .customer(customer)
                .date(new Date())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetails> orderDetails = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Product product = productService.getById(orderDetailRequest.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - orderDetailRequest.getQuantity() < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity Exceeds");
            }


            product.setStock(product.getStock() - orderDetail.getQuantity());
            orderDetailService.createOrUpdate(orderDetail);
            OrderDetails orderDetails1 = OrderDetails.builder()
                    .id(orderDetail.getId())
                    .orderId(order.getId())
                    .product(product)
                    .productPrice(orderDetail.getProductPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();
            orderDetails.add(orderDetails1);

        }

        OrderResponse build = OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .transDate(order.getDate())
                .orderDetails(orderDetails)
                .build();

        return build;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createTransactionCascade(OrderRequest request) {

        Customer customer = customerService.findById(request.getCustomerId());

        Order order = Order.builder()
                .customer(customer)
                .date(new Date())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Product product = productService.getById(orderDetailRequest.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - orderDetailRequest.getQuantity() < 0){
                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity Exceeds");
            }

                product.setStock(product.getStock() - orderDetail.getQuantity());

                productService.update(product);
                orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        return order;
    }

    @Override
    public Order getById(String id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Content Not Found");
        return byId.get();

    }

    @Override
    public Page<Order> getAll(SearchOrderRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageRequest = PageRequest.of(request.getPage() - 1, request.getSize());
        return orderRepository.findAll(pageRequest);
    }


}
