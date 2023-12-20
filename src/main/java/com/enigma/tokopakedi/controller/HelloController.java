package com.enigma.tokopakedi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private List<Map<String, Object>> users = List.of(
            Map.of(
                    "id","1",
                    "name","ujang"
            ),
            Map.of(
                    "id","2",
                    "name","mantap"
            )
    );


    private List<String> fruitsList = List.of(
            "Apel","Mangga","Mangga Muda","Mangga Tua","Nanas","Nanas Tua"
    );

    @GetMapping(path = "/")
    public String helloWorld(){
        return "Hello gaes";
    }
    @GetMapping(path = "/fruits2")
    public List<String> getFruits(){
        return List.of(
                "Buah1","Buah2","Buah3"
        );
    }
    @GetMapping(path = "/cars")
    public Map<String, Object> getCars(){
        return Map.of(
                "brand","Toyota",
                "name","Surya",
                "year",2020
        );
    }

    @GetMapping(path = "/users/{id}")
    public Map<String, Object> getUser(@PathVariable String id){
     Map<String,Object> temp = null;
        for (Map<String, Object> user : users) {
            if(user.get("id").equals(id)){
                temp = user;
            }
        }

        return temp;
    }

    @GetMapping(path = "/fruits")
    public List<String> getFruitsList(@RequestParam String search){
    List<String> result = fruitsList.stream().filter(fruit -> fruit.toLowerCase().contains(search)).collect(Collectors.toList());


    return result;

    };


    @PostMapping(path = "/users")
    public User postUser(@RequestBody User user){
        return user;
    }
}
