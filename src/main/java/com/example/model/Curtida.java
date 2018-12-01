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
@Table(name = "curtidas")
public class Curtida implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="promo_id")
	private Promo promo;
	
	public Curtida() {
		
	}
	
	public Curtida(int id, Promo promo, Usuario usuario) {
		this.id = id;
		this.promo = promo;
		this.usuario = usuario;
	}
	
	public Curtida(Promo promo, Usuario usuario) {
		this.promo = promo;
		this.usuario = usuario;
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
    
    public Promo getPromo() {
    	return this.promo;
    }
    
    public void setPromo(Promo promo) {
    	this.promo = promo;
    }
}