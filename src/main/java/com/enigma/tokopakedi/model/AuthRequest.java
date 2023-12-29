package com.enigma.tokopakedi.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    private String email;
    private String password;

}
