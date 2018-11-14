package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Principal;
import com.example.repository.PrincipalRepository;

@Service
@Transactional(readOnly = true)
public class PrincipalService {

	@Autowired
	private PrincipalRepository principalRepository;

	public List<Principal> findAll() {
		return principalRepository.findAll();
	}
	
	public Optional<Principal> findOne(Integer id) {
		return principalRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Principal save(Principal entity) {
		return principalRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Principal entity) {
		principalRepository.delete(entity);
	}

}
	
