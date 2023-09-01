package com.example.springbootkeyclock.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {


    @GetMapping("/index/client")
    //Specify the user Role
    @PreAuthorize("hasRole('client_user')")
    public String sayHelloClient(){
        return "Hello User :)";
    }

    @GetMapping("/index/admin")
    //Specify the user Role
    @PreAuthorize("hasRole('client_admin')")
    public String sayHelloAdmin(){
        return "Hello Admin :)";
    }
}
