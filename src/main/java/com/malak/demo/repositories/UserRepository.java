package com.malak.demo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malak.demo.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
