package com.RESTfullCRUD.BasicCRUD.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/a")
    public String printhello(){
        return "!!!!Welcome to the CRUD operation!!!!";
    }
}
