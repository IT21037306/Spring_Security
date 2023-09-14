package com.example.springbootkeyclock.controller;

import com.example.springbootkeyclock.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login")
public class Auth {

    @Autowired
    private AuthService authService;

    @Operation(
            description = "Auth operation for Admin & User",
            summary = "Summary of Login endpoint",
            responses = {
                    @ApiResponse(
                            description = "ok",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "not-found",
                            responseCode = "404"
                    )
            }
    )

    @PostMapping("/login")
    public Object login(@RequestBody String usernameAndPassword){

        System.out.println(usernameAndPassword);
        return authService.loginAuth(usernameAndPassword);
    }


}
