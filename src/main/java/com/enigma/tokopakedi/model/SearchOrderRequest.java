package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchOrderRequest {

    private Integer page;
    private Integer size;
}
