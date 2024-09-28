package com.enigma.tokopakedi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.JwtClaim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Service
@Slf4j
public class JwtUtils {

    @Value("${app.tokopakedi.jwt-secret}")
    private String secretKey;
    @Value("${app.tokopakedi.jwt-expirationInSecond}")
    private Long expirationInSecond;
    @Value("${app.tokopakedi.app-name}")
    private String appName;

    public String generateToken(UserCredential user){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            List<String> roles = user.getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();

            return JWT.create()
                    .withIssuer(appName)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
                    .withClaim("roles",roles)
                    .sign(algorithm);
        }catch (JWTCreationException e){
       throw new RuntimeException(e);
        }
    }


    public boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            log.error("Invalid verification JWT : {}",e.getMessage());
            return false;
        }
    }

public JwtClaim getUserInfoByToken(String token){
    try {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

return JwtClaim.builder()
        .userId(decodedJWT.getSubject())
        .roles(roles)
        .build();

    }catch (JWTVerificationException e){
        log.error("Invalid verification JWT : {}",e.getMessage());
        return null;
    }
}
}
