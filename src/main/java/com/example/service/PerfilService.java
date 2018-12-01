package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Perfil;
import com.example.repository.PerfilRepository;

@Service
@Transactional(readOnly = true)
public class PerfilService {
	
	@Autowired
	private PerfilRepository perfilRepository;

	public List<Perfil> findAll() {
		return perfilRepository.findAll();
	}

	public Optional<Perfil> findOne(Integer id) {
		return perfilRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Perfil save(Perfil entity) {
		return perfilRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Perfil entity) {
		perfilRepository.delete(entity);
	}

}
	
