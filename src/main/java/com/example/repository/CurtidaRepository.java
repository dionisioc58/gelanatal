package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Curtida;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Integer> {
	
}