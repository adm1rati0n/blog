package com.example.demo.Repo;

import com.example.demo.models.Auth;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<Auth, Long> {
    Auth findByUsername(String username);
}
