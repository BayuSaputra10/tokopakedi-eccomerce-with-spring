package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.repository.OrderDetailRepository;
import com.enigma.tokopakedi.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    public OrderDetailServiceImpl(OrderDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetail createOrUpdate(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }
}
