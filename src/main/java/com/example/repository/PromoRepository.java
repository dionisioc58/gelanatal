package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Promo;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Integer> {
	
}