package com.enigma.tokopakedi.model;

import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private String customerId;

    private List<OrderDetailRequest> orderDetails;
}
