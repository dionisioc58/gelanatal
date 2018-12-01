package com.example.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "usuarios_perfis")
public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="perfil_id")
	private Perfil perfil;
    
    @Override
	public String toString() {
		return perfil.getNome();
	}
    
    public Integer getId() {
    	return this.id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public Usuario getUsuario() {
    	return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
    	this.usuario = usuario;
    }
    
    public Perfil getPerfil() {
    	return this.perfil;
    }
    
    public void setPerfil(Perfil perfil) {
    	this.perfil = perfil;
    }
}