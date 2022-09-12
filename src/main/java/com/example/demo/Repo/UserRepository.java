package com.example.demo.Repo;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findBySurnameContains(String surname);
}
