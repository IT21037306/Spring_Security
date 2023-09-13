package com.example.springbootkeyclock.controller;

import com.example.springbootkeyclock.model.Item;
import com.example.springbootkeyclock.model.User;
import com.example.springbootkeyclock.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('client_admin')")
@RequestMapping("/api/v1/admin")
public class AdminController {
    
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepo.save(user), HttpStatus.CREATED);
    }

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

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateItem(@PathVariable Integer id, @RequestBody User user){
        Optional<User> newUser = userRepo.findById(id);

        if(newUser.isPresent()){
            newUser.get().setName(user.getName());
            newUser.get().setAddress(user.getAddress());

            return new ResponseEntity<User>(userRepo.save(newUser.get()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Integer id){
        Optional<User> newUser = userRepo.findById(id);

        if(newUser.isPresent()){
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
