package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Promo;
import com.example.repository.PromoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PromoService {

	@Autowired
	private PromoRepository promoRepository;

	public List<Promo> findAll() {
		return promoRepository.findAll();
	}

	public Optional<Promo> findOne(Integer id) {
		return promoRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Promo save(Promo entity) {
		return promoRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Promo entity) {
		promoRepository.delete(entity);
	}

}
	
