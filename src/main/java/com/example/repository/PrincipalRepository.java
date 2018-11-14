package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Principal;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Integer> {
	
}