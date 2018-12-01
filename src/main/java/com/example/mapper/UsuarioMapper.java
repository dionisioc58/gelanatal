package com.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.model.Usuario;
 
public class UsuarioMapper implements RowMapper<Usuario> {
 
    public static final String BASE_SQL //
            //= "Select u.User_Id, u.User_Name, u.Encryted_Password From APP_USER u ";
    		  = "Select u.id, u.nome, u.email, u.senha, u.ativo From usuarios u ";
 
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        int userId = rs.getInt("id");
        String userName = rs.getString("nome");
        String email = rs.getString("email");
        String encrytedPassword = rs.getString("senha");
        Boolean ativo = rs.getBoolean("ativo");
 
        return new Usuario(userId, userName, email, encrytedPassword, ativo);
    }
 
}