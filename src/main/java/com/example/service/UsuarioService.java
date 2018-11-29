package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findOne(Integer id) {
		return usuarioRepository.findById(id);
	}
	
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	@Transactional(readOnly = false)
	public Usuario save(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}

}
	
