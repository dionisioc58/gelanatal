package com.example.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "perfis")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "nome")
    private String nome;
    
    @Override
	public String toString() {
		return nome;
	}
    
    public void setNome(String nome) {this.nome = nome;}
	public String getNome() {return nome;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
}