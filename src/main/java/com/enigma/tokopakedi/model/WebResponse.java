package com.enigma.tokopakedi.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebResponse<T> {

    private String status;
    private String message;
    private PagingResponse paging;
    private T data;
}
