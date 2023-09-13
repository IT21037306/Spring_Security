package com.example.springbootkeyclock.controller;


import com.example.springbootkeyclock.model.Item;
import com.example.springbootkeyclock.repository.ItemRepo;
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
@Tag(name = "Item Management")

//Specify the token auth in swagger
@SecurityRequirement(name = "bearerToken")
@RestController
//Specify the user Role
@PreAuthorize("hasRole('client_user')")
@RequestMapping("/api/v1/user")
public class ClientController {

    @Autowired
    private ItemRepo itemRepo;


    @Operation(
            description = "Add operation of Item by User",
            summary = "Summary of Item POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "created",
                            responseCode = "201"
                    )
            }
    )

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        return new ResponseEntity<Item>(itemRepo.save(item), HttpStatus.CREATED);
    }

    @Operation(
            description = "View operation of Item by User",
            summary = "Summary of Item GET endpoint",
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

    @Operation(
            description = "Edit operation of Item by User",
            summary = "Summary of Item PUT endpoint",
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
    @Operation(
            description = "Delete operation of Item by User",
            summary = "Summary of Item DELETE endpoint",
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
