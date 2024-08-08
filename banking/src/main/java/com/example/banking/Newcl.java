package com.example.banking;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Newcl{

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }
}