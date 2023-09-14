package com.example.springbootkeyclock;

import com.example.springbootkeyclock.model.Item;
import com.example.springbootkeyclock.model.User;
import com.example.springbootkeyclock.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void addUser(){
        User user  = new User();
        user.setName("testUser");
        user.setAddress("testPlace");

        User u = userRepo.save(user);

        assertThat(u).isNotNull();

    }

    @Test
    public void getAllUsers(){
        List<User> userList = userRepo.findAll();
        assertThat(userList.size()).isGreaterThan(0);
    }

    @Test
    public void editUser(){
        Optional<User> user = userRepo.findById(52);
        user.get().setName("newName");
        user.get().setAddress("newAddress");
        userRepo.save(user.get());

        Assertions.assertNotEquals("Nimal",userRepo.findById(52).get().getName());
    }

    @Test
    public void deleteUser(){
        Optional<User> user = userRepo.findById(52);
        userRepo.delete(user.get());

        assertThat(userRepo.existsById(52)).isFalse();

    }


}
