package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    private String id;
    private String orderId;

    private Product product;

    private Long productPrice;
    private Integer quantity;


}
