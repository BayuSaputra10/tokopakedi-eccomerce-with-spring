package com.enigma.tokopakedi.controller;

import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/articles")
public class PostController {

    private final RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<?> getAllPost(){
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        ResponseEntity<List<Article>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Article>>() {
                });

        return exchange;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {
        });
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<>() {
        });
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
//        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null,String.class );
    }




//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody Article article){
//
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {
        String id;
        String title;
        String body;
        String author;
    }
}
