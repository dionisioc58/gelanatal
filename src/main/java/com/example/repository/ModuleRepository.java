package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
}