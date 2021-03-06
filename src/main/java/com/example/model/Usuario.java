package com.example.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Usuario() {
		 
    }
 
    public Usuario(Integer userId, String userName, String email, String encrytedPassword, Boolean ativo) {
        this.id = userId;
        this.nome = userName;
        this.email = email;
        this.senha = encrytedPassword;
        this.ativo = ativo;
    }
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "nome")
    private String nome;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "senha")
    private String senha;
    
    @Column(name = "ativo")
    private Boolean ativo;
	
    @Override
	public String toString() {
		return nome;
	}
    
    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
    List<Promo> promos;
    
    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
    List<UsuarioPerfil> perfis;
	
	public void setNome(String nome) {this.nome = nome;}
	public String getNome() {return nome;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	
	public List<Promo> getPromos() { return promos; }
	public void setPromos(List<Promo> promos) { this.promos = promos; }
	
	public List<UsuarioPerfil> getPerfis() { return perfis; }
	public void setPerfis(List<UsuarioPerfil> perfis) { this.perfis = perfis; }
	
	public Boolean getAtivo() { return ativo; }
	public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}