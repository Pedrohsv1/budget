package com.pedro.budget.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.pedro.budget.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByUsername(String username);
}
