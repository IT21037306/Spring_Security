package com.example.springbootkeyclock.repository;

import com.example.springbootkeyclock.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Integer> {
}
