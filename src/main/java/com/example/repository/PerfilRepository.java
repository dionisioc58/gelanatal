package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
	
}