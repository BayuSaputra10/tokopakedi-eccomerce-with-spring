package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {

    List<Customer> findAllByNameContaining(String name);
    List<Customer> findAllByphoneNumberContaining(String phoneNumber);

}
