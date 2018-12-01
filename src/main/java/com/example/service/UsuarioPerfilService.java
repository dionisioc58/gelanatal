package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.UsuarioPerfil;
import com.example.repository.UsuarioPerfilRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioPerfilService {
	
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	public List<UsuarioPerfil> findAll() {
		return usuarioPerfilRepository.findAll();
	}

	public Optional<UsuarioPerfil> findOne(Integer id) {
		return usuarioPerfilRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public UsuarioPerfil save(UsuarioPerfil entity) {
		return usuarioPerfilRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(UsuarioPerfil entity) {
		usuarioPerfilRepository.delete(entity);
	}

}
	
