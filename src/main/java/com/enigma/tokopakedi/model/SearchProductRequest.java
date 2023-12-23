package com.enigma.tokopakedi.model;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductRequest {

    private Integer page;

    private Integer size;

    private String name;

    private Integer minPrice;

    private Integer maxPrice;
}
