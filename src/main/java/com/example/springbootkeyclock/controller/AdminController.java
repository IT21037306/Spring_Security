package com.example.springbootkeyclock.controller;

import com.example.springbootkeyclock.model.User;
import com.example.springbootkeyclock.repository.UserRepo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//Heading of API
@Tag(name = "User Management")
//Specify the token auth in swagger
@SecurityRequirement(name = "bearerToken")
@RestController
@PreAuthorize("hasRole('client_admin')")
@RequestMapping("/api/v1/admin")
public class AdminController {
    
    @Autowired
    private UserRepo userRepo;

    @Operation(
            description = "Add operation of User by Admin",
            summary = "Summary of User POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "created",
                            responseCode = "201"
                    )
            }
    )

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepo.save(user), HttpStatus.CREATED);
    }

    @Operation(
            description = "View operation of User by Admin",
            summary = "Summary of User GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "found",
                            responseCode = "302"
                    ),
                    @ApiResponse(
                            description = "not-found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/users")
    public ResponseEntity<List<User>> displayUsers(){
        List<User> userList = userRepo.findAll();

        if (!userList.isEmpty()){
            return new ResponseEntity<>(userList,HttpStatus.FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(
            description = "Edit operation of User by Admin",
            summary = "Summary of User PUT endpoint",
            responses = {
                    @ApiResponse(
                            description = "created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "not-found",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user){
        Optional<User> newUser = userRepo.findById(id);

        if(newUser.isPresent()){
            newUser.get().setName(user.getName());
            newUser.get().setAddress(user.getAddress());

            return new ResponseEntity<User>(userRepo.save(newUser.get()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(
            description = "Delete operation of User by Admin",
            summary = "Summary of User DELETE endpoint",
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
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id){
        Optional<User> newUser = userRepo.findById(id);

        if(newUser.isPresent()){
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
