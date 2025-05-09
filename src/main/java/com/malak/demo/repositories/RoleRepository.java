package com.malak.demo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malak.demo.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{
	Optional<Role> findByName(String name);
}
