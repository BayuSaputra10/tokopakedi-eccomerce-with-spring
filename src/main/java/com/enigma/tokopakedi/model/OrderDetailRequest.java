package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailRequest {

    private String productId;

    private Integer quantity;
}
