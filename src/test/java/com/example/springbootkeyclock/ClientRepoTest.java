package com.example.springbootkeyclock;

import com.example.springbootkeyclock.model.Item;
import com.example.springbootkeyclock.repository.ItemRepo;
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

public class ClientRepoTest {

    @Autowired
    private ItemRepo itemRepo;

    @Test
    public void addItem(){
        Item item = new Item();
        item.setItemName("testItem");
        item.setQuantity(10);

        Item svItem = itemRepo.save(item);

        assertThat(svItem).isNotNull();

    }

    @Test
    public void readAllItem(){
        List<Item> itemList = itemRepo.findAll();
        assertThat(itemList.size()).isGreaterThan(0);

    }

    @Test
    public void editItem(){
        Optional<Item> item = itemRepo.findById(2);
        item.get().setItemName("SonyPhone");
        itemRepo.save(item.get());

        Assertions.assertNotEquals("iPhone",itemRepo.findById(2).get().getItemName());

    }

    @Test
    public void deleteItem(){
        Optional<Item> item = itemRepo.findById(2);
        itemRepo.delete(item.get());

        assertThat(itemRepo.existsById(2)).isFalse();

    }
}
