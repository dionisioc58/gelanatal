package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AppUsuarioDAO;
import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AppUsuarioDAO appUsuarioDAO;

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findOne(Integer id) {
		return usuarioRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Usuario save(Usuario entity) throws Exception {
		if(entity.getId() == null) {
			if(appUsuarioDAO.findUserByEmail(entity.getEmail()) != null) {
				throw new Exception("Email já cadastrado!");
			}
		}
		String encoded = new BCryptPasswordEncoder().encode(entity.getSenha());
		entity.setSenha(encoded);
		return usuarioRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}

}
	
