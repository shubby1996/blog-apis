package com.project.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	/*
	 * Querying by example for spring security CustomUserDetailsService which implements UserDetailsService 
	 */
	
    Optional<User> findByEmail(String email);
	
}
