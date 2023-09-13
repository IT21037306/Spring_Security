package com.example.springbootkeyclock.repository;

import com.example.springbootkeyclock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
