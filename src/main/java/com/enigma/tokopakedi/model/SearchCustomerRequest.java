package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCustomerRequest {

    private Integer page;

    private Integer size;

    private String name;

    private String address;

    private String phoneNumber;

}
