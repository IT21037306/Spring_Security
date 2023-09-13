package com.example.springbootkeyclock.controller;


import com.example.springbootkeyclock.model.Item;
import com.example.springbootkeyclock.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//Specify the user Role
@PreAuthorize("hasRole('client_user')")
@RequestMapping("/api/v1/user")
public class ClientController {

    @Autowired
    private ItemRepo itemRepo;

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        return new ResponseEntity<Item>(itemRepo.save(item), HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> displayItems(){
        List<Item> itemList = itemRepo.findAll();

        if (!itemList.isEmpty()){
            return new ResponseEntity<>(itemList,HttpStatus.FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Integer id, @RequestBody Item item){
        Optional<Item> newItem = itemRepo.findById(id);

        if(newItem.isPresent()){
            newItem.get().setItemName(item.getItemName());
            newItem.get().setQuantity(item.getQuantity());

            return new ResponseEntity<Item>(itemRepo.save(newItem.get()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Integer id){
        Optional<Item> newItem = itemRepo.findById(id);

        if(newItem.isPresent()){
            itemRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
