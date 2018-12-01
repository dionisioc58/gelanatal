package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Curtida;
import com.example.repository.CurtidaRepository;

@Service
@Transactional(readOnly = true)
public class CurtidaService {
	
	@Autowired
	private CurtidaRepository curtidaRepository;

	public List<Curtida> findAll() {
		return curtidaRepository.findAll();
	}

	public Optional<Curtida> findOne(Integer id) {
		return curtidaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Curtida save(Curtida entity) {
		return curtidaRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Curtida entity) {
		curtidaRepository.delete(entity);
	}

}
	
